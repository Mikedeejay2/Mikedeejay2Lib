package com.mikedeejay2.mikedeejay2lib.util.structure;

public class NavigationHolder<T> extends HistoryHolder<T> implements IdentityHolder<String>
{
    /**
     * The navigation ID
     */
    protected String navigationID;

    /**
     * Whether navigation has recently occurred
     */
    protected boolean hasNavigated;

    /**
     * Construct a new <code>NavigationHolder</code>
     *
     * @param navigationID The navigation ID
     */
    public NavigationHolder(String navigationID)
    {
        this.navigationID = navigationID;
        this.hasNavigated = false;
    }

    /**
     * Get the ID of the navigation holder
     *
     * @return The ID
     */
    @Override
    public String getID()
    {
        return navigationID;
    }

    /**
     * Set the new ID of the navigation holder
     *
     * @param id The new ID
     */
    public void setID(String id)
    {
        this.navigationID = id;
    }

    /**
     * Get whether navigation has been flagged.
     * Used in some cases for checking whether an object has been opened or navigated to.
     *
     * @return The navigation flag
     */
    public boolean isNavigationFlagged()
    {
        return hasNavigated;
    }

    /**
     * Set the navigation flag status.
     * Used in some cases for checking whether an object has been opened or navigated to.
     *
     * @param hasNavigated The new navigation flag
     */
    public void setNavigationFlag(boolean hasNavigated)
    {
        this.hasNavigated = hasNavigated;
    }
}
