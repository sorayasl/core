/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.gmap.api;

import org.apache.wicket.util.string.Strings;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.js.ObjectLiteral;

/**
 * http://code.google.com/apis/maps/documentation/javascript/reference.html# MarkerOptions
 *
 * @author Christian Hennig (christian.hennig@freiheit.com)
 * @author Joachim F. Rohde
 */
public class GMarkerOptions implements GValue, Cloneable
{

    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private boolean clickable = true;
    private final String cursor = null;
    private boolean draggable = false;
    private final boolean flat = false;
    private GIcon icon = null;
    private final GMap gmap;
    private String title;
    private GLatLng latLng;
    private boolean bouncy = true;
    private boolean autoPan = false;
    private GIcon shadow = null;
    private GAnimation animation = null;

    public GMarkerOptions(GMap gmap, GLatLng latLng)
    {
        this.latLng = latLng;
        this.gmap = gmap;
    }

    /**
     * Creates a GMarkerOptions instance with the specified parameters. 
     * 
     * @param gmap The GMap-instance the marker will be put on
     * @param latLng the position the marker will be placed
     * @param title the tooltip text for this marker. The most common special sequences in the title will be escaped
     * @see GMap
     * @see GLatLng
     * @see #GMarkerOptions(org.wicketstuff.gmap.GMap, org.wicketstuff.gmap.api.GLatLng, java.lang.String, boolean) 
     */
    public GMarkerOptions(GMap gmap, GLatLng latLng, String title)
    {
        this(gmap, latLng, title, true);
    }

    /**
     * @param gmap The GMap-instance the marker will be placed on
     * @param latLng the position the marker will be placed
     * @param title the tooltip text for this marker
     * @param escapeTitle escapes backslashes(\), quotes ("), newlines (\n), tabs (\r) and carriage returns (\r) in the title, if set to true
     */
    public GMarkerOptions(GMap gmap, GLatLng latLng, String title, boolean escapeTitle)
    {
        this(gmap, latLng);
        this.title = title;
        if (escapeTitle)
        {
            this.title = Strings.replaceAll(this.title, "\\", "\\\\").toString();
            this.title = Strings.replaceAll(this.title, "\n", "\\n").toString();
            this.title = Strings.replaceAll(this.title, "\r", "\\r").toString();
            this.title = Strings.replaceAll(this.title, "\t", "\\t").toString();
            this.title = Strings.replaceAll(this.title, "\"", "\\\"").toString();
        }
    }

    public GMarkerOptions(GMap gmap, GLatLng latLng, String title, GIcon icon)
    {
        this(gmap, latLng, title);
        this.icon = icon;
    }

    public GMarkerOptions(GMap gmap, GLatLng latLng, String title, GIcon icon, boolean escapeTitle)
    {
        this(gmap, latLng, title, escapeTitle);
        this.icon = icon;
    }

    /**
     * Do not use this constructor anymore since it will be removed with WicketStuff 1.7.
     * @deprecated Marker shadows were removed in version 3.14 of the Google Maps JavaScript API. 
     * Any shadows specified programmatically will be ignored.
     * @see https://developers.google.com/maps/documentation/javascript/markers#complex_icons
     */
    public GMarkerOptions(GMap gmap, GLatLng latLng, String title, GIcon icon, GIcon shadow)
    {
        this(gmap, latLng, title, icon, true);
        this.shadow = shadow;
    }

    /**
     * @see GValue#getJSconstructor()
     */
    @Override
    public String getJSconstructor()
    {
        ObjectLiteral literal = new ObjectLiteral();

        literal.set("map", gmap.getJsReference() + ".map");
        literal.set("position", latLng.getJSconstructor());

        if (!clickable)
        {
            literal.set("clickable", "false");
        }
        if (cursor != null)
        {
            literal.set("cursor", cursor);
        }
        if (draggable)
        {
            literal.set("draggable", "true");
        }
        if (flat)
        {
            literal.setString("flat", "true");
        }
        if (icon != null)
        {
            literal.set("icon", icon.getJSconstructor());
        }
        if (shadow != null)
        {
            literal.set("shadow", shadow.getJSconstructor());
        }
        if (title != null)
        {
            literal.setString("title", title);
        }
        if (!bouncy)
        {
            literal.set("bouncy", "false");
        }
        if (autoPan)
        {
            literal.set("autoPan", "true");
        }
        if (animation != null)
        {
            literal.set("animation", animation.toString());
        }        

        return literal.toJS();
    }

    public String getTitle()
    {
        return title;
    }

    public boolean isDraggable()
    {
        return draggable;
    }

    public boolean isClickable()
    {
        return clickable;
    }
       
    /**
     * Do not use this method since it will be removed with WicketStuff 1.7.
     * @deprecated This has been removed by Google Maps. 
     * @see org.wicketstuff.gmap.api.GMarker#setAnimation(org.wicketstuff.gmap.api.GAnimation) 
     */
    public boolean isBouncy()
    {
        return bouncy;
    }

    /**
     * Do not use this method since it will be removed with WicketStuff 1.7.
     * @deprecated This has been removed by Google Maps. 
     */    
    public boolean isAutoPan()
    {
        return autoPan;
    }

    public GIcon getIcon()
    {
        return icon;
    }

    /**
     * @see java.lang.Object#clone()
     */
    @Override
    public GMarkerOptions clone()
    {
        try
        {
            return (GMarkerOptions) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new Error(e);
        }
    }

    public GMarkerOptions clickable(boolean clickable)
    {
        GMarkerOptions clone = clone();
        clone.clickable = clickable;
        return clone;
    }

    public GMarkerOptions draggable(boolean draggable)
    {
        GMarkerOptions clone = clone();
        clone.draggable = draggable;
        return clone;
    }

    /**
     * Do not use this method since it will be removed with WicketStuff 1.7.
     * @deprecated This has been removed by Google Maps. 
     */    
    public GMarkerOptions autoPan(boolean autoPan)
    {
        GMarkerOptions clone = clone();
        clone.autoPan = autoPan;
        return clone;
    }

    /**
     * Do not use this method since it will be removed with WicketStuff 1.7.
     * @deprecated This has been removed by Google Maps. 
     * @see org.wicketstuff.gmap.api.GMarker#setAnimation(org.wicketstuff.gmap.api.GAnimation) 
     */    
    public GMarkerOptions bouncy(boolean bouncy)
    {
        GMarkerOptions clone = clone();
        clone.bouncy = bouncy;
        return clone;
    }

    public String getCursor()
    {
        return cursor;
    }

    /**
     * Do not use this method since it will be removed with WicketStuff 1.7.
     * @deprecated This has been removed by Google Maps. 
     */    
    public boolean isFlat()
    {
        return flat;
    }

    public void setLatLng(GLatLng latLng)
    {
        this.latLng = latLng;
    }

    public GLatLng getLatLng()
    {
        return latLng;
    }

    public GMap getGmap()
    {
        return gmap;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + (autoPan
                ? 1231
                : 1237);
        result = PRIME * result + (bouncy
                ? 1231
                : 1237);
        result = PRIME * result + (clickable
                ? 1231
                : 1237);
        result = PRIME * result + (draggable
                ? 1231
                : 1237);
        result = PRIME * result + ((icon == null)
                ? 0
                : icon.hashCode());
        result = PRIME * result + ((title == null)
                ? 0
                : title.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final GMarkerOptions other = (GMarkerOptions) obj;
        if (autoPan != other.autoPan)
        {
            return false;
        }
        if (bouncy != other.bouncy)
        {
            return false;
        }
        if (clickable != other.clickable)
        {
            return false;
        }
        if (draggable != other.draggable)
        {
            return false;
        }
        if (icon == null)
        {
            if (other.icon != null)
            {
                return false;
            }
        }
        else if (!icon.equals(other.icon))
        {
            return false;
        }
        if (title == null)
        {
            if (other.title != null)
            {
                return false;
            }
        }
        else if (!title.equals(other.title))
        {
            return false;
        }
        return true;
    }

    
    /**
     * Do not use this method since it will be removed with WicketStuff 1.7.
     * @deprecated Marker shadows were removed in version 3.14 of the Google Maps JavaScript API. 
     * Any shadows specified programmatically will be ignored.
     * @see https://developers.google.com/maps/documentation/javascript/markers#complex_icons
     */
    public void setShadow(GIcon shadow)
    {
        this.shadow = shadow;
    }

    /**
     * Do not use this method since it will be removed with WicketStuff 1.7.
     * @deprecated Marker shadows were removed in version 3.14 of the Google Maps JavaScript API. 
     * Any shadows specified programmatically will be ignored.
     * @see https://developers.google.com/maps/documentation/javascript/markers#complex_icons
     */
    public GIcon getShadow()
    {
        return shadow;
    }
    
    public GAnimation getAnimation() 
    {
        return animation;
    }

    public void setAnimation(GAnimation animation) 
    {        
        this.animation = animation;
    }
}
