package com.dbquality.checks.xml;

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

import com.dbquality.custom.checks.elements.CustomRootElement;
import com.dbquality.custom.checks.xml.CustomModule;
import com.dbquality.distinct.checks.elements.DistinctRootElement;
import com.dbquality.distinct.checks.xml.DistinctModule;
import com.dbquality.xsd.ResourceResolver;

/**
 * 
 * @author George Kolokotronis
 * 
 *         Class which parses the xml with the checks
 *
 */
public final class ChecksCreatorUtils {

	private static final Logger logger = LogManager.getLogger(ChecksCreatorUtils.class);

	private ChecksCreatorUtils() {
		// to avoid instantiation
	}

	/**
	 * Creates a DistinctRootElement object based on XML data.
	 * 
	 * @param xmlFilePath
	 *            - name of the XML file
	 */
	public static DistinctRootElement loadDistinctChecks(String xmlFilePath, String xsdPath) {

		DistinctRootElement result = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(xmlFilePath);
		} catch (FileNotFoundException e) {
			logger.error("Cannot find file " + xmlFilePath);
			throw new RuntimeException("Cannot find file " + xmlFilePath, e);
		}

		validateXMLSchema(xmlFilePath, xsdPath);
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
	 * Creates a CustomRootElement object based on XML data.
	 * 
	 * @param xmlFilePath
	 *            - name of the XML file
	 */
	public static CustomRootElement loadCustomChecks(String xmlFilePath, String xsdPath) {

		CustomRootElement result = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(xmlFilePath);
		} catch (FileNotFoundException e) {
			logger.error("Cannot find file " + xmlFilePath);
			throw new RuntimeException("Cannot find file " + xmlFilePath, e);
		}

		validateXMLSchema(xmlFilePath, xsdPath);
		DigesterLoader digesterLoader = DigesterLoader.newLoader(new CustomModule());
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
	public static void validateXMLSchema(String xmlPath, String xsdPath) {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);

		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		DocumentBuilder parser = null;
		Document document = null;
		try {

			parser = builderFactory.newDocumentBuilder();

		} catch (ParserConfigurationException e) {
			logger.error("Something went wrong while loading xsd file " + xsdPath + "from the root of the jar file");
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

		Source schemaFile = new StreamSource(ChecksCreatorUtils.class.getClassLoader().getResourceAsStream(xsdPath));
		Schema schema = null;

		try {
			schema = factory.newSchema(schemaFile);
		} catch (SAXException e) {
			logger.error("Something went wrong while parsing XSD file " + xsdPath);
			throw new IllegalArgumentException("Something went wrong while parsing XSD file " + xsdPath, e);

		}

		Validator validator = schema.newValidator();

		try {
			validator.validate(new DOMSource(document));
		} catch (SAXException | IOException e) {
			logger.error("Error while validating file: " + xmlPath + " with " + xsdPath);
			throw new RuntimeException(
					"Something went wrong while validating XML " + xmlPath + " with XSD file " + xsdPath, e);
		}

	}
}