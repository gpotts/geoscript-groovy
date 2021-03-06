package geoscript.style.io

import geoscript.style.Style
import org.geotools.factory.CommonFactoryFinder
import org.geotools.styling.SLDTransformer
import org.geotools.styling.StyleFactory
import org.geotools.styling.StyledLayerDescriptor
import org.geotools.styling.UserLayer

/**
 * Write a Style to an SLD document
 * <p><blockquote><pre>
 * def sym = new Fill("#ff00FF") + new Stroke("#ffff00", 0.25")
 * def writer = new SLDWriter()
 * writer.write(sym)
 * </pre></blockquote></p>
 * @author Jared Erickson
 */
class SLDWriter implements Writer {

    /**
     * Whether to format the SLD or not
     */
    boolean format = true
   
    /**
     * Write the Style to the OutputStream
     * @param The Style
     * @param The OutputStream
     */
    void write(Style style, OutputStream out) {
        StyleFactory sf = CommonFactoryFinder.getStyleFactory(null)
        UserLayer userLayer = sf.createUserLayer()
        userLayer.addUserStyle(style.gtStyle)
        StyledLayerDescriptor sld = sf.createStyledLayerDescriptor()
        sld.addStyledLayer(userLayer)
        def transformer = new SLDTransformer()
        if (format) {
            transformer.indentation = 2
        }
        transformer.transform(sld, out)
    }

    /**
     * Write the Style to the File
     * @param style The Style
     * @param file The File
     */
    void write(Style style, File file) {
        FileOutputStream out = new FileOutputStream(file)
        write(style, out)
        out.close()
    }

    /**
     * Write the Style to a String
     * @param The Style
     * @return A String
     */
    String write(Style style) {
        ByteArrayOutputStream out = new ByteArrayOutputStream()
        write(style, out);
        out.close()
        return out.toString()
    }
}