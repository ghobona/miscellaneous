package org.opengis.cite.geomatics;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.opengis.cite.geomatics.Extents;
import org.opengis.cite.geomatics.gml.GmlUtils;
import org.opengis.cite.geomatics.time.TemporalComparator;
import org.opengis.cite.geomatics.time.TemporalUtils;

import org.opengis.temporal.Period;
import org.opengis.temporal.TemporalGeometricPrimitive;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

public class Example {
	
	

	public static void main(String[] args) {
		
		String URLPath = "http://192.71.92.21/MapService/wfs.axd/InspireTest?service=WFS&version=2.0.0&request=GetFeature&TypeNames=tn-ro:RoadLink";
		GeometryFactory factory = new GeometryFactory(); 
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new URL(URLPath).openStream()));
            doc.getDocumentElement().normalize();
      
            NodeList curveList = doc.getElementsByTagNameNS("http://www.opengis.net/gml/3.2","Curve");
            for(int a=0; a<curveList.getLength(); a++)
            {
            	Element curveElement = (Element) curveList.item(a);
            	NodeList posList = curveElement.getElementsByTagNameNS("http://www.opengis.net/gml/3.2","posList");
            	System.out.println("Check "+a);
            	for(int b=0; b<posList.getLength(); b++)
            	{
            		Element posListElement = (Element) posList.item(b);
            		System.out.println("Check "+posListElement.getTextContent());
            		String[] coords = posListElement.getTextContent().trim().split(" ");
            		ArrayList<Coordinate> coordinateList = new ArrayList<Coordinate>();
                    for(int c=0; c < coords.length; c = c + 2)
                    {
                    	coordinateList.add(new Coordinate(Double.parseDouble(coords[c]),Double.parseDouble(coords[c+1])));
                    	System.out.println(coords[c]+","+coords[c+1]);
                    }
                    Coordinate[] coordinates = new Coordinate[coordinateList.size()];
                    coordinates = coordinateList.toArray(coordinates);
                    LineString line = factory.createLineString(coordinates);
                    Envelope env = line.getEnvelopeInternal();
            	}
            }
       
        }
        catch (Exception er)
        {
        	er.printStackTrace();
        }

	}

}
