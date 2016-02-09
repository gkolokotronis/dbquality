package com.dbquality.distinct.checks.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.dbquality.consts.AppConsts;
import com.dbquality.distinct.checks.elements.DistinctRootElement;
import com.dbquality.xsd.ResourceResolver;

/**
 * 
 * @author George Kolokotronis
 * 
 *         Class which parses the xml with the checks
 *
 */
public class DistinctChecksCreator {

	private static final Logger logger = LogManager.getLogger(DistinctChecksCreator.class);

	/**
	 * Creates a DistinctRootElement object based on XML data.
	 * 
	 * @param xmlFilePath
	 *            - name of the XML file
	 */
	public DistinctRootElement loadDQChecks(String xmlFilePath) {

		DistinctRootElement result = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(xmlFilePath);
		} catch (FileNotFoundException e) {
			logger.error("Cannot find file " + xmlFilePath);
			throw new RuntimeException("Cannot find file " + xmlFilePath, e);
		}

		validateXMLSchema(xmlFilePath);
		DigesterLoader digesterLoader = DigesterLoader.newLoader(new DistinctModule());
		Digester digester = digesterLoader.newDigester();

		try {
			result = digester.parse(inputStream);
		} catch (IOException e) {
			logger.error("Input/outpur error while parsing xml file " + xmlFilePath);
			throw new RuntimeException("Input/outpur error while parsing xml file " + xmlFilePath, e);
		} catch (SAXException e) {
			logger.error("Error while parsing xml file " + xmlFilePath);
			throw new RuntimeException("Error while parsing xml file " + xmlFilePath, e);
		}

		return result;
	}

	/**
	 * Validates the xml structure, and it validates it against the
	 * application's xsd
	 * 
	 * @param xmlPath
	 *            - xml file to be validated *
	 */
	public void validateXMLSchema(String xmlPath) {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);

		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		DocumentBuilder parser = null;
		Document document = null;
		try {

			parser = builderFactory.newDocumentBuilder();

		} catch (ParserConfigurationException e) {
			logger.error("Something went wrong while loading xsd file " + AppConsts.XSD_LOCATION
					+ "from the root of the jar file");
			throw new RuntimeException("Something went wrong while loading xsd file ", e);
		}

		// parse the XML into a document object
		File xmlFile = new File(xmlPath);
		FileInputStream xmlFileInput = null;
		try {
			xmlFileInput = new FileInputStream(xmlFile);
		} catch (FileNotFoundException e) {
			logger.error("XML file " + xmlPath + " not found");
			throw new IllegalArgumentException("XML file " + xmlPath + " not found", e);
		}

		try {
			document = parser.parse(xmlFileInput);
		} catch (SAXException | IOException e) {
			logger.error("Something went wrong while parsing XML file " + xmlPath + " to DOM object");
			throw new IllegalArgumentException(
					"Something went wrong while parsing XML file " + xmlPath + " to DOM object", e);
		}

		// associate the schema factory with the resource resolver, which is
		// responsible for resolving the imported XSD's
		factory.setResourceResolver(new ResourceResolver());

		Source schemaFile = new StreamSource(getClass().getClassLoader().getResourceAsStream(AppConsts.XSD_LOCATION));
		Schema schema = null;

		try {
			schema = factory.newSchema(schemaFile);
		} catch (SAXException e) {
			logger.error("Something went wrong while parsing XSD file " + AppConsts.XSD_LOCATION);
			throw new IllegalArgumentException("Something went wrong while parsing XSD file " + AppConsts.XSD_LOCATION,
					e);

		}

		Validator validator = schema.newValidator();

		try {
			validator.validate(new DOMSource(document));
		} catch (SAXException | IOException e) {
			logger.error("Error while validating file: " + xmlPath + " with " + AppConsts.XSD_LOCATION);
			throw new RuntimeException(
					"Something went wrong while validating XML " + xmlPath + " with XSD file " + AppConsts.XSD_LOCATION,
					e);
		}

	}
}