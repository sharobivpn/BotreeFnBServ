package com.botree.restaurantapp.testing;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.io.FilePermission;
import java.util.ArrayList;
import java.util.Locale;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.StreamPrintService;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PageRanges;
import javax.print.attribute.standard.PrinterState;
import javax.print.attribute.standard.PrinterStateReason;
import javax.print.attribute.standard.PrinterStateReasons;
import javax.print.attribute.standard.Sides;



public  class RasterPrinterJob2  {

 /* Class Constants */

     /* Printer destination type. */
    protected static final int PRINTER = 0;

     /* File destination type.  */
    protected static final int FILE = 1;

    /* Stream destination type.  */
    protected static final int STREAM = 2;

    /**
     * Maximum amount of memory in bytes to use for the
     * buffered image "band". 4Mb is a compromise between
     * limiting the number of bands on hi-res printers and
     * not using too much of the Java heap or causing paging
     * on systems with little RAM.
     */
    private static final int MAX_BAND_SIZE = (1024 * 1024 * 4);

    /* Dots Per Inch */
    private static final float DPI = 72.0f;

    /**
     * Useful mainly for debugging, this system property
     * can be used to force the printing code to print
     * using a particular pipeline. The two currently
     * supported values are FORCE_RASTER and FORCE_PDL.
     */
    private static final String FORCE_PIPE_PROP = "sun.java2d.print.pipeline";

    /**
     * When the system property FORCE_PIPE_PROP has this value
     * then each page of a print job will be rendered through
     * the raster pipeline.
     */
    private static final String FORCE_RASTER = "raster";

    /**
     * When the system property FORCE_PIPE_PROP has this value
     * then each page of a print job will be rendered through
     * the PDL pipeline.
     */
    private static final String FORCE_PDL = "pdl";

    /**
     * When the system property SHAPE_TEXT_PROP has this value
     * then text is always rendered as a shape, and no attempt is made
     * to match the font through GDI
     */
    private static final String SHAPE_TEXT_PROP = "sun.java2d.print.shapetext";

    /**
     * values obtained from System properties in static initialiser block
     */
    public static boolean forcePDL = false;
    public static boolean forceRaster = false;
    public static boolean shapeTextProp = false;

    static {
        /* The system property FORCE_PIPE_PROP
         * can be used to force the printing code to
         * use a particular pipeline. Either the raster
         * pipeline or the pdl pipeline can be forced.
         */
        String forceStr ="";
          
        String shapeTextStr ="";
           
    }

    /* Instance Variables */

    /**
     * Used to minimise GC & reallocation of band when printing
     */
    private int cachedBandWidth = 0;
    private int cachedBandHeight = 0;
    private BufferedImage cachedBand = null;

    /**
     * The number of book copies to be printed.
     */
    private int mNumCopies = 1;

    /**
     * Collation effects the order of the pages printed
     * when multiple copies are requested. For two copies
     * of a three page document the page order is:
     *  mCollate true: 1, 2, 3, 1, 2, 3
     *  mCollate false: 1, 1, 2, 2, 3, 3
     */
    private boolean mCollate = false;

    /**
     * The zero based indices of the first and last
     * pages to be printed. If 'mFirstPage' is
     * UNDEFINED_PAGE_NUM then the first page to
     * be printed is page 0. If 'mLastPage' is
     * UNDEFINED_PAGE_NUM then the last page to
     * be printed is the last one in the book.
     */
    private int mFirstPage = Pageable.UNKNOWN_NUMBER_OF_PAGES;
    private int mLastPage = Pageable.UNKNOWN_NUMBER_OF_PAGES;

    /**
     * The previous print stream Paper
     * Used to check if the paper size has changed such that the
     * implementation needs to emit the new paper size information
     * into the print stream.
     * Since we do our own rotation, and the margins aren't relevant,
     * Its strictly the dimensions of the paper that we will check.
     */
    private Paper previousPaper;

    /**
     * The document to be printed. It is initialized to an
     * empty (zero pages) book.
     */
    private Pageable mDocument = new Book();

    /**
     * The name of the job being printed.
     */
    private String mDocName = new String("Java Printing");


    /**
     * Printing cancellation flags
     */
    private boolean performingPrinting = false;
    private boolean userCancelled = false;

   /**
    * Print to file permission variables.
    */
    private FilePermission printToFilePermission;

    /**
     * List of areas & the graphics state for redrawing
     */
    private ArrayList redrawList = new ArrayList();


    /* variables representing values extracted from an attribute set.
     * These take precedence over values set on a printer job
     */
    private int copiesAttr;
    private String jobNameAttr;
    private String userNameAttr;
    private PageRanges pageRangesAttr;
    protected Sides sidesAttr;
    protected String destinationAttr;
    protected boolean noJobSheet = false;
    protected String mDestination = "";
    protected boolean collateAttReq = false;

    /**
     * Device rotation flag, if it support 270, this is set to true;
     */
    protected boolean landscapeRotates270 = false;

   /**
     * attributes used by no-args page and print dialog and print method to
     * communicate state
     */
    protected PrintRequestAttributeSet attributes = null;

    /**
     * Class to keep state information for redrawing areas
     * "region" is an area at as a high a resolution as possible.
     * The redrawing code needs to look at sx, sy to calculate the scale
     * to device resolution.
     */
    private class GraphicsState {
        Rectangle2D region;  // Area of page to repaint
        Shape theClip;       // image drawing clip.
        AffineTransform theTransform; // to transform clip to dev coords.
        double sx;           // X scale from region to device resolution
        double sy;           // Y scale from region to device resolution
    }

    /**
     * Service for this job
     */
    protected PrintService myService;

 /* Constructors */

    public RasterPrinterJob2()
    {
    }


/* Instance Methods */

    /**
      * save graphics state of a PathGraphics for later redrawing
      * of part of page represented by the region in that state
      */

    public void saveState(AffineTransform at, Shape clip,
                          Rectangle2D region, double sx, double sy) {
        GraphicsState gstate = new GraphicsState();
        gstate.theTransform = at;
        gstate.theClip = clip;
        gstate.region = region;
        gstate.sx = sx;
        gstate.sy = sy;
        redrawList.add(gstate);
    }


    /*
     * A convenience method which returns the default service
     * for 2D <code>PrinterJob</code>s.
     * May return null if there is no suitable default (although there
     * may still be 2D services available).
     * @return default 2D print service, or null.
     * @since     1.4
     */
    protected static PrintService lookupDefaultPrintService() {
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();

        /* Pageable implies Printable so checking both isn't strictly needed */
        if (service != null &&
            service.isDocFlavorSupported(
                                DocFlavor.SERVICE_FORMATTED.PAGEABLE) &&
            service.isDocFlavorSupported(
                                DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
            return service;
        } else {
           PrintService []services =
             PrintServiceLookup.lookupPrintServices(
                                DocFlavor.SERVICE_FORMATTED.PAGEABLE, null);
           if (services.length > 0) {
               return services[0];
           }
        }
        return null;
    }

   /**
     * Returns the service (printer) for this printer job.
     * Implementations of this class which do not support print services
     * may return null;
     * @return the service for this printer job.
     *
     */
    public PrintService getPrintService() {
        if (myService == null) {
            PrintService svc = PrintServiceLookup.lookupDefaultPrintService();
            if (svc != null &&
                svc.isDocFlavorSupported(
                     DocFlavor.SERVICE_FORMATTED.PAGEABLE)) {
                try {
                    setPrintService(svc);
                    myService = svc;
                } catch (PrinterException e) {
                }
            }
            if (myService == null) {
                PrintService[] svcs = PrintServiceLookup.lookupPrintServices(
                    DocFlavor.SERVICE_FORMATTED.PAGEABLE, null);
                if (svcs.length > 0) {
                    try {
                        setPrintService(svcs[0]);
                        myService = svcs[0];
                    } catch (PrinterException e) {
                    }
                }
            }
        }
        return myService;
    }

    /**
     * Associate this PrinterJob with a new PrintService.
     *
     * Throws <code>PrinterException</code> if the specified service
     * cannot support the <code>Pageable</code> and
     * <code>Printable</code> interfaces necessary to support 2D printing.
     * @param a print service which supports 2D printing.
     *
     * @throws PrinterException if the specified service does not support
     * 2D printing or no longer available.
     */
    public void setPrintService(PrintService service)
        throws PrinterException {
        if (service == null) {
            throw new PrinterException("Service cannot be null");
        } else if (!(service instanceof StreamPrintService) &&
                   service.getName() == null) {
            throw new PrinterException("Null PrintService name.");
        } else {
            // Check the list of services.  This service may have been
            // deleted already
            PrinterState prnState = (PrinterState)service.getAttribute(
                                                  PrinterState.class);
            if (prnState == PrinterState.STOPPED) {
                PrinterStateReasons prnStateReasons =
                    (PrinterStateReasons)service.getAttribute(
                                                 PrinterStateReasons.class);
                if ((prnStateReasons != null) &&
                    (prnStateReasons.containsKey(PrinterStateReason.SHUTDOWN)))
                {
                    throw new PrinterException("PrintService is no longer available.");
                }
            }


            if (service.isDocFlavorSupported(
                                             DocFlavor.SERVICE_FORMATTED.PAGEABLE) &&
                service.isDocFlavorSupported(
                                             DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
                myService = service;
            } else {
                throw new PrinterException("Not a 2D print service: " + service);
            }
        }
    }


    protected void updatePageAttributes(PrintService service,
                                        PageFormat page) {
        if (service == null || page == null) {
            return;
        }

        float x = (float)Math.rint(
                         (page.getPaper().getWidth()*Size2DSyntax.INCH)/
                         (72.0))/(float)Size2DSyntax.INCH;
        float y = (float)Math.rint(
                         (page.getPaper().getHeight()*Size2DSyntax.INCH)/
                         (72.0))/(float)Size2DSyntax.INCH;

        // We should limit the list where we search the matching
        // media, this will prevent mapping to wrong media ex. Ledger
        // can be mapped to B.  Especially useful when creating
        // custom MediaSize.
        Media[] mediaList = (Media[])service.getSupportedAttributeValues(
                                      Media.class, null, null);
        Media media = null;
        try {
            
        } catch (IllegalArgumentException iae) {
        }
        if ((media == null) ||
             !(service.isAttributeValueSupported(media, null, null))) {
            media = (Media)service.getDefaultAttributeValue(Media.class);
        }

        OrientationRequested orient;
        switch (page.getOrientation()) {
        case PageFormat.LANDSCAPE :
            orient = OrientationRequested.LANDSCAPE;
            break;
        case PageFormat.REVERSE_LANDSCAPE:
            orient = OrientationRequested.REVERSE_LANDSCAPE;
            break;
        default:
            orient = OrientationRequested.PORTRAIT;
        }

        if (attributes == null) {
            attributes = new HashPrintRequestAttributeSet();
        }
        if (media != null) {
            attributes.add(media);
        }
        attributes.add(orient);

        float ix = (float)(page.getPaper().getImageableX()/DPI);
        float iw = (float)(page.getPaper().getImageableWidth()/DPI);
        float iy = (float)(page.getPaper().getImageableY()/DPI);
        float ih = (float)(page.getPaper().getImageableHeight()/DPI);
        if (ix < 0) ix = 0f; if (iy < 0) iy = 0f;
        try {
            attributes.add(new MediaPrintableArea(ix, iy, iw, ih,
                                                  MediaPrintableArea.INCH));
        } catch (IllegalArgumentException iae) {
        }
    }

   /**
     * Display a dialog to the user allowing the modification of a
     * PageFormat instance.
     * The <code>page</code> argument is used to initialize controls
     * in the page setup dialog.
     * If the user cancels the dialog, then the method returns the
     * original <code>page</code> object unmodified.
     * If the user okays the dialog then the method returns a new
     * PageFormat object with the indicated changes.
     * In either case the original <code>page</code> object will
     * not be modified.
     * @param     page    the default PageFormat presented to the user
     *                    for modification
     * @return    the original <code>page</code> object if the dialog
     *            is cancelled, or a new PageFormat object containing
     *            the format indicated by the user if the dialog is
     *            acknowledged
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since     1.2
     */
    public PageFormat pageDialog(PageFormat page)
        throws HeadlessException {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        }

        final GraphicsConfiguration gc =
          GraphicsEnvironment.getLocalGraphicsEnvironment().
          getDefaultScreenDevice().getDefaultConfiguration();

        PrintService service =
            (PrintService)java.security.AccessController.doPrivileged(
                                        new java.security.PrivilegedAction() {
                public Object run() {
                    PrintService service = getPrintService();
                    if (service == null) {
                       // ServiceDialog.showNoPrintService(gc);
                        return null;
                    }
                    return service;
                }
            });

        if (service == null) {
            return page;
        }
        updatePageAttributes(service, page);

        PageFormat newPage = null; //pageDialog(attributes);

        if (newPage == null) {
            return page;
        } else {
            return newPage;
        }
    }

    

   /**
     * Presents the user a dialog for changing properties of the
     * print job interactively.
     * The services browsable here are determined by the type of
     * service currently installed.
     * If the application installed a StreamPrintService on this
     * PrinterJob, only the available StreamPrintService (factories) are
     * browsable.
     *
     * @param attributes to store changed properties.
     * @return false if the user cancels the dialog and true otherwise.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
   /**
     * Presents the user a dialog for changing properties of the
     * print job interactively.
     * @returns false if the user cancels the dialog and
     *          true otherwise.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
 

    protected void initPrinter() {
        return;
    }

    protected boolean isSupportedValue(Attribute attrval,
                                     PrintRequestAttributeSet attrset) {
        PrintService ps = getPrintService();
        return
            (attrval != null && ps != null &&
             ps.isAttributeValueSupported(attrval,
                                          DocFlavor.SERVICE_FORMATTED.PAGEABLE,
                                          attrset));
    }

    /* subclasses may need to pull extra information out of the attribute set
     * They can override this method & call super.setAttributes()
     */
   /*
     * Services we don't recognize as built-in services can't be
     * implemented as subclasses of PrinterJob, therefore we create
     * a DocPrintJob from their service and pass a Doc representing
     * the application's printjob
   * Prints a set of pages.
     * @exception java.awt.print.PrinterException an error in the print system
     *                                          caused the job to be aborted
     * @see java.awt.print.Book
     * @see java.awt.print.Pageable
     * @see java.awt.print.Printable
     */
    

    public static boolean debugPrint = false;
 

      protected void validatePaper(Paper origPaper, Paper newPaper) {
        if (origPaper == null || newPaper == null) {
            return;
        } else {
            double wid = origPaper.getWidth();
            double hgt = origPaper.getHeight();
            double ix = origPaper.getImageableX();
            double iy = origPaper.getImageableY();
            double iw = origPaper.getImageableWidth();
            double ih = origPaper.getImageableHeight();

            /* Assume any +ve values are legal. Overall paper dimensions
             * take precedence. Make sure imageable area fits on the paper.
             */
            Paper defaultPaper = new Paper();
            wid = ((wid > 0.0) ? wid : defaultPaper.getWidth());
            hgt = ((hgt > 0.0) ? hgt : defaultPaper.getHeight());
            ix = ((ix > 0.0) ? ix : defaultPaper.getImageableX());
            iy = ((iy > 0.0) ? iy : defaultPaper.getImageableY());
            iw = ((iw > 0.0) ? iw : defaultPaper.getImageableWidth());
            ih = ((ih > 0.0) ? ih : defaultPaper.getImageableHeight());
            /* full width/height is not likely to be imageable, but since we
             * don't know the limits we have to allow it
             */
            if (iw > wid) {
                iw = wid;
            }
            if (ih > hgt) {
                ih = hgt;
            }
            if ((ix + iw) > wid) {
                ix = wid - iw;
            }
            if ((iy + ih) > hgt) {
                iy = hgt - ih;
            }
            newPaper.setSize(wid, hgt);
            newPaper.setImageableArea(ix, iy, iw, ih);
        }
    }

    /**
     * The passed in PageFormat will be copied and altered to describe
     * the default page size and orientation of the PrinterJob's
     * current printer.
     * Platform subclasses which can access the actual default paper size
     * for a printer may override this method.
     */
    public PageFormat defaultPage(PageFormat page) {
        PageFormat newPage = (PageFormat)page.clone();
        newPage.setOrientation(PageFormat.PORTRAIT);
        Paper newPaper = new Paper();
        double ptsPerInch = 72.0;
        double w, h;
        Media media = null;

        PrintService service = getPrintService();
        if (service != null) {
            MediaSize size;
            media =
                (Media)service.getDefaultAttributeValue(Media.class);

            if (media instanceof MediaSizeName &&
               ((size = MediaSize.getMediaSizeForName((MediaSizeName)media)) !=
                null)) {
                w =  size.getX(MediaSize.INCH) * ptsPerInch;
                h =  size.getY(MediaSize.INCH) * ptsPerInch;
                newPaper.setSize(w, h);
                newPaper.setImageableArea(ptsPerInch, ptsPerInch,
                                          w - 2.0*ptsPerInch,
                                          h - 2.0*ptsPerInch);
                newPage.setPaper(newPaper);
                return newPage;

            }
        }

        /* Default to A4 paper outside North America.
         */
        String defaultCountry = Locale.getDefault().getCountry();
        if (!Locale.getDefault().equals(Locale.ENGLISH) && // ie "C"
            defaultCountry != null &&
            !defaultCountry.equals(Locale.US.getCountry()) &&
            !defaultCountry.equals(Locale.CANADA.getCountry())) {

            double mmPerInch = 25.4;
            w = Math.rint((210.0*ptsPerInch)/mmPerInch);
            h = Math.rint((297.0*ptsPerInch)/mmPerInch);
            newPaper.setSize(w, h);
            newPaper.setImageableArea(ptsPerInch, ptsPerInch,
                                      w - 2.0*ptsPerInch,
                                      h - 2.0*ptsPerInch);
        }

        newPage.setPaper(newPaper);

        return newPage;
    }

    /**
     * The passed in PageFormat is cloned and altered to be usable on
     * the PrinterJob's current printer.
     */
    public PageFormat validatePage(PageFormat page) {
        PageFormat newPage = (PageFormat)page.clone();
        Paper newPaper = new Paper();
        validatePaper(newPage.getPaper(), newPaper);
        newPage.setPaper(newPaper);

        return newPage;
    }

    /**
     * Set the number of copies to be printed.
     */
    public void setCopies(int copies) {
        mNumCopies = copies;
    }

    /**
     * Get the number of copies to be printed.
     */
    public int getCopies() {
        return mNumCopies;
    }

   /* Used when executing a print job where an attribute set may
     * over ride API values.
     */
    protected int getCopiesInt() {
        return (copiesAttr > 0) ? copiesAttr : getCopies();
    }

    /**
     * Get the name of the printing user.
     * The caller must have security permission to read system properties.
     */
    public String getUserName() {
        return System.getProperty("user.name");
    }

   /* Used when executing a print job where an attribute set may
     * over ride API values.
     */
    protected String getUserNameInt() {
        if  (userNameAttr != null) {
            return userNameAttr;
        } else {
            try {
                return  getUserName();
            } catch (SecurityException e) {
                return "";
            }
        }
    }

    /**
     * Set the name of the document to be printed.
     * The document name can not be null.
     */
    public void setJobName(String jobName) {
        if (jobName != null) {
            mDocName = jobName;
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Get the name of the document to be printed.
     */
    public String getJobName() {
        return mDocName;
    }

    /* Used when executing a print job where an attribute set may
     * over ride API values.
     */
    protected String getJobNameInt() {
        return (jobNameAttr != null) ? jobNameAttr : getJobName();
    }

    /**
     * Set the range of pages from a Book to be printed.
     * Both 'firstPage' and 'lastPage' are zero based
     * page indices. If either parameter is less than
     * zero then the page range is set to be from the
     * first page to the last.
     */
    protected void setPageRange(int firstPage, int lastPage) {
        if(firstPage >= 0 && lastPage >= 0) {
            mFirstPage = firstPage;
            mLastPage = lastPage;
            if(mLastPage < mFirstPage) mLastPage = mFirstPage;
        } else {
            mFirstPage = Pageable.UNKNOWN_NUMBER_OF_PAGES;
            mLastPage = Pageable.UNKNOWN_NUMBER_OF_PAGES;
        }
    }

    /**
     * Return the zero based index of the first page to
     * be printed in this job.
     */
    protected int getFirstPage() {
        return mFirstPage == Book.UNKNOWN_NUMBER_OF_PAGES ? 0 : mFirstPage;
    }

    /**
     * Return the zero based index of the last page to
     * be printed in this job.
     */
    protected int getLastPage() {
        return mLastPage;
    }

    /**
     * Set whether copies should be collated or not.
     * Two collated copies of a three page document
     * print in this order: 1, 2, 3, 1, 2, 3 while
     * uncollated copies print in this order:
     * 1, 1, 2, 2, 3, 3.
     * This is set when request is using an attribute set.
     */
    protected void setCollated(boolean collate) {
        mCollate = collate;
        collateAttReq = true;
    }

    /**
     * Return true if collated copies will be printed as determined
     * in an attribute set.
     */
    protected boolean isCollated() {
            return mCollate;
    }

   

    /**
     * Returns how many times the entire book should
     * be printed by the PrintJob. If the printer
     * itself supports collation then this method
     * should return 1 indicating that the entire
     * book need only be printed once and the copies
     * will be collated and made in the printer.
     */
    protected int getCollatedCopies() {
        return isCollated() ? getCopiesInt() : 1;
    }

    /**
     * Returns how many times each page in the book
     * should be consecutively printed by PrintJob.
     * If the printer makes copies itself then this
     * method should return 1.
     */
    protected int getNoncollatedCopies() {
        return isCollated() ? 1 : getCopiesInt();
    }


    /* The printer graphics config is cached on the job, so that it can
     * be created once, and updated only as needed (for now only to change
     * the bounds if when using a Pageable the page sizes changes).
     */

    private int deviceWidth, deviceHeight;
    private AffineTransform defaultDeviceTransform;
 
    public void cancel() {
        synchronized (this) {
            if (performingPrinting) {
                userCancelled = true;
            }
            notify();
        }
    }

    /**
     * Returns true is a print job is ongoing but will
     * be cancelled and the next opportunity. false is
     * returned otherwise.
     */
    public boolean isCancelled() {

        boolean cancelled = false;

        synchronized (this) {
            cancelled = (performingPrinting && userCancelled);
            notify();
        }

        return cancelled;
    }

    /**
     * Return the Pageable describing the pages to be printed.
     */
    protected Pageable getPageable() {
        return mDocument;
    }

    /**
     * Examine the metrics captured by the
     * <code>PeekGraphics</code> instance and
     * if capable of directly converting this
     * print job to the printer's control language
     * or the native OS's graphics primitives, then
     * return a <code>PathGraphics</code> to perform
     * that conversion. If there is not an object
     * capable of the conversion then return
     * <code>null</code>. Returning <code>null</code>
     * causes the print job to be rasterized.
     */
 
    /**
     * Create and return an object that will
     * gather and hold metrics about the print
     * job. This method is passed a <code>Graphics2D</code>
     * object that can be used as a proxy for the
     * object gathering the print job matrics. The
     * method is also supplied with the instance
     * controlling the print job, <code>printerJob</code>.
     */
 

    /**
     * Configue the passed in Graphics2D so that
     * is contains the defined initial settings
     * for a print job. These settings are:
     *      color:  black.
     *      clip:   <as passed in>
     */
    void initPrinterGraphics(Graphics2D g, Rectangle2D clip) {

        g.setClip(clip);
        g.setPaint(Color.black);
    }


   /**
    * User dialogs should disable "File" buttons if this returns false.
    *
    */
    public boolean checkAllowedToPrintToFile() {
        try {
            throwPrintToFile();
            return true;
        } catch (SecurityException e) {
            return false;
        }
    }

    /**
     * Break this out as it may be useful when we allow API to
     * specify printing to a file. In that case its probably right
     * to throw a SecurityException if the permission is not granted
     */
    private void throwPrintToFile() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            if (printToFilePermission == null) {
                printToFilePermission =
                    new FilePermission("<<ALL FILES>>", "read,write");
            }
            security.checkPermission(printToFilePermission);
        }
    }

    /* On-screen drawString renders most control chars as the missing glyph
     * and have the non-zero advance of that glyph.
     * Exceptions are \t, \n and \r which are considered zero-width.
     * This is a utility method used by subclasses to remove them so we
     * don't have to worry about platform or font specific handling of them.
     */
    protected String removeControlChars(String s) {
        char[] in_chars = s.toCharArray();
        int len = in_chars.length;
        char[] out_chars = new char[len];
        int pos = 0;

        for (int i = 0; i < len; i++) {
            char c = in_chars[i];
            if (c > '\r' || c < '\t' || c == '\u000b' || c == '\u000c')  {
               out_chars[pos++] = c;
            }
        }
        if (pos == len) {
            return s; // no need to make a new String.
        } else {
            return new String(out_chars, 0, pos);
        }
    }
}
