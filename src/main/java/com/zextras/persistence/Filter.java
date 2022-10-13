package com.zextras.persistence;

import com.unboundid.ldap.sdk.SearchScope;

/**
 * A filter class is used to determine whether an entry contains a specified attribute value. If an
 * entry includes the specified value, regardless of whether it has any other values for the target
 * attribute, then that entry will match the filter for that value. If an entry does not contain any
 * values for the attribute, or if none of the values matches the target value, then that entry will
 * not match the filter.
 * <p>
 * You can create a filter from the constructors or {@link #builder() method}.
 */
public class Filter {

  private String dn;
  private String filter;
  private SearchScope searchScope;

  public Filter() {
  }

  public Filter(final String filter) {
    this.filter = filter;
  }

  /**
   * Creates a filter based on dn, search scope and assertion value.
   *
   * @param dn          It can be null then the dn to search will be set with the base dn provided
   *                    to the client.
   * @param filter      a string representation of an attribute with assertion value that should be
   *                    constructed as follows:
   *                    <p>
   *                    1. An open parenthesis.
   *                    <p>
   *                    2. The attribute description (potentially including attribute options).
   *                    <p>
   *                    3. An equal sign.
   *                    <p>
   *                    4. The value to compare (aka the assertion value).
   *                    <p>
   *                    5. A close parenthesis.
   *                    <p>
   *                    ex. "(cn=Santa Claus)"
   *                    <p>
   *                    6. Multiple filter attributes should be included in the "&()" and divided by
   *                    a comma. ex. "&((cn=Santa Claus),(sn=Claus))".
   * @param searchScope the scope that specifies the range of entries that should be examined for
   *                    the search.
   */
  public Filter(final String dn, final String filter, final SearchScope searchScope) {
    this.dn = dn;
    this.filter = filter;
    this.searchScope = searchScope;
  }


  public String getFilter() {
    return filter;
  }

  public void setFilter(final String filter) {
    this.filter = filter;
  }

  public SearchScope getSearchScope() {
    return searchScope;
  }

  public void setSearchScope(final SearchScope searchScope) {
    this.searchScope = searchScope;
  }

  /**
   * Creates a builder class in order to easily build sting representation of the filter.
   *
   * @return {@link Builder}.
   */
  public static Builder builder() {
    return new Builder();
  }

  public String getDn() {
    return dn;
  }

  public void setDn(final String dn) {
    this.dn = dn;
  }

  /**
   * Builder class allows constructing complex filter objects step by step.
   */
  public static class Builder {

    private final Filter filter = new Filter();
    private final StringBuilder builder = new StringBuilder();

    protected Builder() {

    }

    /**
     * Sets the DN for the filter that would be used in a search request.
     *
     * @param dn It can be null then the dn to search will be set with the base dn provided to the
     *           client.
     * @return {@link Builder}.
     */
    public Builder dn(final String dn) {
      this.filter.setDn(dn);
      return this;
    }

    /**
     * Applies a single value filter, can be used multiple times.
     *
     * @param filterToApply a string representation is constructed as follows:
     *                      <p>
     *                      1. The attribute description (potentially including attribute options).
     *                      <p>
     *                      2. An equal sign.
     *                      <p>
     *                      3. The value to compare (aka the assertion value).
     *                      <p>
     *                      ex. "cn=Santa Claus"
     * @return {@link Builder}.
     */
    public Builder applyFilter(final String filterToApply) {
      if (builder.length() == 0) {
        builder.append("(&");
      }
      builder
          .append("(")
          .append(filterToApply)
          .append(")");
      return this;
    }

    /**
     * Sets a search scope for the filter.
     *
     * @param searchScope the scope that specifies the range of entries that should be examined for
     *                    the search.
     * @return {@link Builder}.
     */
    public Builder searchScope(final SearchScope searchScope) {
      this.filter.setSearchScope(searchScope);
      return this;
    }

    /**
     * Completes building a filter.
     *
     * @return built {@link Filter}.
     */
    public Filter build() {
      builder.append(")");
      this.filter.setFilter(this.builder.toString());
      return this.filter;
    }
  }
}
