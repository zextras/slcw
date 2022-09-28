package com.zextras.operations.ldap;

import com.unboundid.ldap.sdk.*;
import com.zextras.operations.AbstractOperationExecutor;
import com.zextras.operations.OperationResult;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.mapping.SlcwEntry;

import java.util.Collection;
import java.util.List;

public class LdapOperationExecutor extends AbstractOperationExecutor {
    private LDAPConnection connection;
    public LdapOperationExecutor(LDAPConnection connection) {
        this.connection = connection;
    }

    public OperationResult executeAddOperation(SlcwEntry entry) {
        Collection<Attribute> attributes = (Collection<Attribute>) entry.getAttributes();
        try {
            LDAPResult result =  connection.add(entry.getDn(), attributes);
            return new OperationResult(result.getResultCode().getName(), result.getResultCode().intValue());
        } catch (LDAPException e) {
            throw new RuntimeException(e);
        }
    }

    public OperationResult executeUpdateOperation(SlcwEntry entry) {
        List<Modification> modifications = (List<Modification>) entry.getAttributes();
        try {
            LDAPResult result = connection.modify(entry.getDn(), modifications);
            return new OperationResult(result.getResultCode().getName(), result.getResultCode().intValue());
        } catch (LDAPException e) {
            throw new RuntimeException(e);
        }
    }

    public OperationResult executeDeleteOperation(SlcwEntry entry) {
        try {
            LDAPResult result = connection.delete(entry.getDn());
            return new OperationResult(result.getResultCode().getName(), result.getResultCode().intValue());
        } catch (LDAPException e) {
            throw new RuntimeException(e);
        }
    }

    public OperationResult executeGetOperation(SlcwEntry entry) {
        String filter = entry.getId().getFieldName() + "=" + entry.getId().getFiledValue();

        var result = search(entry.getDn(), SearchScope.ONE, filter);
        var searchResultEntries = result.getSearchEntries();
        if (searchResultEntries.isEmpty()) {
            throw new SlcwException(String.format("Object %s not found.", entry.getId().getFiledValue()));
        }

        entry.setAttributes(searchResultEntries.get(0).getAttributes());
        return new OperationResult(result.getResultCode().getName(), result.getResultCode().intValue());
    }

    private SearchResult search(String baseDN, SearchScope searchScope, String filter) {
        SearchResult searchResult;
        try {
            searchResult = connection.search(baseDN, searchScope, filter);
        } catch (LDAPSearchException e) {
            throw new RuntimeException(e);
        }
        return searchResult;
    }
}
