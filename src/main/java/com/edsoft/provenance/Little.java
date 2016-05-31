package com.edsoft.provenance;


import java.util.Arrays;
import java.util.Random;

import com.edsoft.iot.Data;
import org.openprovenance.prov.interop.InteropFramework;
import org.openprovenance.prov.interop.InteropFramework.ProvFormat;
import org.openprovenance.prov.model.*;

/**
 * A little provenance goes a long way.
 * ProvToolbox Tutorial 1: creating a provenance document in Java and serializing it
 * to SVG (in a file) and to PROVN (on the console).
 *
 * @author lucmoreau
 * @see <a href="http://blog.provbook.org/2013/10/11/a-little-provenance-goes-a-long-way/">a-little-provenance-goes-a-long-way blog post</a>
 */
public class Little {


    public static final String PROVBOOK_NS = "http://www.provbook.org";
    public static final String PROVBOOK_PREFIX = "provbook";

    public static final String JIM_PREFIX = "jim";
    public static final String JIM_NS = "http://www.cs.rpi.edu/~hendler/";

    private static ProvFactory pFactory;
    private static Namespace ns = null;

    public Little(ProvFactory pFactory) {
        Little.pFactory = pFactory;
        ns = new Namespace();
        ns.addKnownNamespaces();
        ns.register(PROVBOOK_PREFIX, PROVBOOK_NS);
        ns.register(JIM_PREFIX, JIM_NS);
    }

    public static QualifiedName qn(String n) {
        return ns.qualifiedName(PROVBOOK_PREFIX, n, pFactory);
    }

    public Document makeDocument(Data testData) {
        Entity quote = pFactory.newEntity(qn(String.valueOf(Math.abs(testData.getValue()))));
        quote.setValue(pFactory.newValue(testData.getValue(),
                pFactory.getName().XSD_STRING));


        // Entity original = pFactory.newEntity(ns.qualifiedName(JIM_PREFIX, "LittleSemanticsWeb.html", pFactory));

        Agent paul = pFactory.newAgent(qn(String.valueOf(testData.getId())), testData.getSensorType());
        //Agent luc = pFactory.newAgent(qn("Luc"), "Luc Moreau");

        Activity paulActivity = pFactory.newActivity(qn("T"), "Generate");


        WasGeneratedBy generatedBy1 = pFactory.newWasGeneratedBy(null,
                paulActivity.getId(), quote.getId());


        WasAttributedTo attr1 = pFactory.newWasAttributedTo(null,
                quote.getId(),
                paul.getId());

        Used used = pFactory.newUsed(null,
                quote.getId(), paulActivity.getId());

        WasAssociatedWith associatedWith = pFactory.newWasAssociatedWith(
                null, paulActivity.getId(), paul.getId()
        );
        /*WasAttributedTo attr2 = pFactory.newWasAttributedTo(null,
                quote.getId(),
                luc.getId());*/

       /* WasDerivedFrom wdf = pFactory.newWasDerivedFrom(quote.getId(),
                original.getId());*/

        Document document = pFactory.newDocument();
        document.getStatementOrBundle()
                .addAll(Arrays.asList(quote,
                        paul,
                        used,
                        generatedBy1,
                        paulActivity,
                        associatedWith,
                        attr1));
        document.setNamespace(ns);
        return document;
    }

    public void doConversions(Document document, String file) {
        InteropFramework intF = new InteropFramework();
        intF.writeDocument(file, document);
        intF.writeDocument(System.out, ProvFormat.PROVN, document);
    }

    public void closingBanner() {
     /*   System.out.println("");
        System.out.println("*************************");*/
    }

    public void openingBanner() {
       /* System.out.println("*************************");
        System.out.println("* Converting document  ");
        System.out.println("*************************");*/
    }

    public static Document convertDocument(Data data) {
        Little little = new Little(InteropFramework.newXMLProvFactory());
        little.openingBanner();
        Document document = little.makeDocument(data);
        little.closingBanner();
        return document;
    }

    public static Data convertData(Document document) {
        Data data = new Data();
        Entity entity = (Entity) document.getStatementOrBundle().get(0);
        data.setValue((Integer) entity.getValue().getValue());
        Agent agent = (Agent) document.getStatementOrBundle().get(1);
        data.setId(Integer.parseInt(agent.getId().getLocalPart()));
        data.setSensorType(agent.getLabel().get(0).getValue());
        return data;
    }
}