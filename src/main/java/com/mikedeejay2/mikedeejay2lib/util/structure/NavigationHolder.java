package com.mikedeejay2.mikedeejay2lib.util.structure;

/**
 * A {@link HistoryHolder} implementing an {@link IdentityHolder} intended to hold the history of a navigable system.
 *
 * @param <T> The data being stored
 * @author Mikedeejay2
 */
public class NavigationHolder<T> extends HistoryHolder<T> implements IdentityHolder<String> {
    /**
     * The navigation ID
     */
    protected String navigationID;

    /**
     * Construct a new <code>NavigationHolder</code>
     *
     * @param navigationID The navigation ID
     */
    public NavigationHolder(String navigationID) {
        this.navigationID = navigationID;
    }

    /**
     * Get the ID of the navigation holder
     *
     * @return The ID
     */
    @Override
    public String getID() {
        return navigationID;
    }

    /**
     * Set the new ID of the navigation holder
     *
     * @param id The new ID
     */
    public void setID(String id) {
        this.navigationID = id;
    }
}
