package esa.uebungen.jws5;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.jee.esa.uebungen.jws4.AbstractProduct;
import org.dieschnittstelle.jee.esa.uebungen.jws4.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.uebungen.jws4.ProductCRUDWebService;
import org.dieschnittstelle.jee.esa.uebungen.jws4.ProductCRUDWebService_Service;
import org.dieschnittstelle.jee.esa.uebungen.jws4.ProductType;

/*
 * UE JWS5: rufen Sie hier den in JWS4 implementierten Web Service auf.
 */
public class TestProductCRUDWebService {
	
	private static final Logger logger = LogManager.getLogger(TestProductCRUDWebService.class.getName());
	
	public static void main(String[] args) {

		// create two products and add them to the list of products
		IndividualisedProductItem prod1 = new IndividualisedProductItem();
		prod1.setName("Schrippe");
		prod1.setProductType(ProductType.ROLL);
		prod1.setPrice(720);
		
		IndividualisedProductItem prod2 = new IndividualisedProductItem();
		prod2.setName("Kirschplunder");
		prod2.setProductType(ProductType.PASTRY);
		prod2.setPrice(1080);
		
		/*
		 * initialisieren ein Service Interface fuer den in JWS4 erstellten Web Service
		 */		
		ProductCRUDWebService_Service service = new ProductCRUDWebService_Service();
		
		ProductCRUDWebService productOperations = service.getProductCRUDWebServicePort();
		
		/*
		 * rufen Sie die im Interface deklarierte Methode fuer das Erzeugen von Produkten fuer prod1 und prod2 auf und geben Sie die Rueckgabewerte auf der Kosole aus.
		 */
		
		{
			AbstractProduct p = productOperations.createProduct(prod1);
			logger.info("created product: {}", p);
		}
		
		{
			AbstractProduct p = productOperations.createProduct(prod2);
			logger.info("created product: {}", p);
		}
		
		/*
		 * rufen Sie die im Interface deklarierte Methode fuer das Auslesen aller Produkte auf und geben Sie den Rueckgabewert auf der Konsole aus.
		 */
		
		{
			List<AbstractProduct> p = productOperations.readAllProducts().getItem();
			logger.info("read all products: {}", p);
		}

	}
	
}
