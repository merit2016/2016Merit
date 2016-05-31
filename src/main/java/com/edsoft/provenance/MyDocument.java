package com.edsoft.provenance;

import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.Namespace;
import org.openprovenance.prov.model.StatementOrBundle;

import java.util.List;

/**
 * Created by edsoft on 26.02.2016.
 */
public class MyDocument implements Document {

    public MyDocument() {
    }

    @Override
    public Namespace getNamespace() {
        return null;
    }

    @Override
    public List<StatementOrBundle> getStatementOrBundle() {
        return null;
    }

    @Override
    public void setNamespace(Namespace namespace) {

    }
}
