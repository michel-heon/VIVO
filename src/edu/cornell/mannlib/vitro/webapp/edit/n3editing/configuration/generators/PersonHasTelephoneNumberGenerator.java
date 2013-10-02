/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.vocabulary.XSD;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.ChildVClassesOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.IndividualsViaVClassOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;

public class PersonHasTelephoneNumberGenerator extends VivoBaseGenerator implements
        EditConfigurationGenerator {
    private Log log = LogFactory.getLog(PersonHasTelephoneNumberGenerator.class);
       
    @Override
    public EditConfigurationVTwo getEditConfiguration(VitroRequest vreq,
            HttpSession session) throws Exception {
        
        EditConfigurationVTwo conf = new EditConfigurationVTwo();
        
        initBasics(conf, vreq);
        initPropertyParameters(vreq, session, conf);
        initObjectPropForm(conf, vreq);  
        String phoneUri = getPhoneUri(vreq);  
        
        conf.setTemplate("personHasTelephoneNumber.ftl");
        
        conf.setVarNameForSubject("person");
        conf.setVarNameForPredicate("predicate");
        conf.setVarNameForObject("individualVcard");
        
        conf.setN3Required( Arrays.asList( n3ForNewPhone ) );
        conf.setN3Optional( Arrays.asList( telephoneNumberAssertion ) );
        
        conf.addNewResource("phone", DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource("individualVcard", DEFAULT_NS_FOR_NEW_RESOURCE);
                
        conf.setLiteralsOnForm(Arrays.asList("telephoneNumber" ));
        
        conf.addSparqlForExistingLiteral("telephoneNumber", telephoneNumberQuery);
        conf.addSparqlForAdditionalUrisInScope("individualVcard", individualVcardQuery);
        
        if ( conf.isUpdate() ) {
            HashMap<String, List<String>> urisInScope = new HashMap<String, List<String>>();
            urisInScope.put("phone", Arrays.asList(new String[]{phoneUri}));
            conf.addUrisInScope(urisInScope);
        }

        conf.addField( new FieldVTwo().                        
                setName("telephoneNumber")
                .setRangeDatatypeUri( XSD.xstring.toString() ).
                setValidators( list("nonempty") ));
        
        conf.addValidator(new AntiXssValidation());
        
        prepare(vreq, conf);
        return conf;
    }

    /* N3 assertions  */

    final static String n3ForNewPhone = 
        "?person <http://purl.obolibrary.org/obo/ARG_2000028>  ?individualVcard . \n" +
        "?individualVcard a <http://www.w3.org/2006/vcard/ns#Individual> . \n" +              
        "?individualVcard <http://purl.obolibrary.org/obo/ARG_2000029> ?person . \n" +
        "?individualVcard <http://www.w3.org/2006/vcard/ns#hasTelephone> ?phone . \n" +
        "?phone a <http://www.w3.org/2006/vcard/ns#Telephone> . " ;    
    
    final static String telephoneNumberAssertion  =      
        "?phone <http://www.w3.org/2006/vcard/ns#telephone> ?telephoneNumber .";
    
    /* Queries for editing an existing entry */

    final static String individualVcardQuery =
        "SELECT ?existingIndividualVcard WHERE { \n" +
        "?person <http://purl.obolibrary.org/obo/ARG_2000028>  ?existingIndividualVcard . \n" +
        "}";

    final static String telephoneNumberQuery  =      
        "SELECT ?existingTelephoneNumber WHERE {\n"+
        "?phone <http://www.w3.org/2006/vcard/ns#telephone> ?existingTelephoneNumber . }";

	private String getPhoneUri(VitroRequest vreq) {
        String phoneUri = vreq.getParameter("phoneUri"); 
        
		return phoneUri;
	}
}