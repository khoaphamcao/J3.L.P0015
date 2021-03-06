/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Khoa Pham
 */
public class FilterDispatcher implements Filter {

    private static final boolean debug = true;
    private static final Map<String, String> mapping = new HashMap<>();

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public FilterDispatcher() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("FilterDispatcher:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("FilterDispatcher:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

//        HttpServletRequest req = (HttpServletRequest) request;
//        String uri = req.getRequestURI();
//        String url = "DefaultSearchServlet";
//        try {
//            int lastIndex = uri.lastIndexOf("/");
//            String resource = uri.substring(lastIndex + 1);
//            if (resource != null) {
//                if (resource.contains("?")) {
//                    String[] split = resource.split("?");
//                    url = mapping.get(split[0]);
//                } else {
//                    url = mapping.get(resource);
//                }
//            }
//            if (resource.length() == 0) {
//                url = mapping.get("");
//            }
//            if (url != null) {
//                RequestDispatcher rd = req.getRequestDispatcher(url);
//                rd.forward(request, response);
//            } else {
//                chain.doFilter(request, response);
//            }
//        } catch (Exception e) {
//        }
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        String url = mapping.get("");

        try {
            int lastIndex = uri.lastIndexOf("/");
            String resource = uri.substring(lastIndex + 1);

            if (resource.length() > 0) {
                if (resource.contains("?")) {
                    String[] split = resource.split("?");
                    url = mapping.get(split[0]);
                } else {
                    url = mapping.get(resource);
                }
            }

            if (url != null) {
                RequestDispatcher rd = req.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            //------------------------
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
//        this.filterConfig = filterConfig;
//        this.filterConfig = filterConfig;
//        FileReader f = null;
//        BufferedReader br = null;
//        ServletContext context = filterConfig.getServletContext();
//        String web = "/WEB-INF/web.txt";
////        Map parameter = null;
//        String asoblutePath = filterConfig.getServletContext().getRealPath(web);
////        System.out.println(asoblutePath);
//        try {
//            f = new FileReader(asoblutePath);
//            br = new BufferedReader(f);
//
//            while (br.ready()) {
//                String line = br.readLine();
//                String[] par = line.split("=");
//                String name = par[0];
//                String value = par[1];
//                mapping.put(name, value);
//                context.setAttribute(name, value);
//            }
//        } catch (Exception e) {
//        } finally {
//            try {
//                if (br != null) {
//                    br.close();
//                }
//                if (f != null) {
//                    f.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        if (filterConfig != null) {
//            if (debug) {
//                log("FilterDispatcher:Initializing filter");
//            }
//        }
        try {
            mapping.put("", "renting_car.jsp");
            mapping.put("default", "DefaultSearchServlet");
            mapping.put("search", "SearchServlet");
            mapping.put("signUp", "SignUpServlet");
            mapping.put("login", "LoginServlet");
            mapping.put("logout", "LogoutServlet");
            mapping.put("updateDiscount", "UpdateDiscountCodeServlet");
            mapping.put("addToCart", "AddCarToCartServlet");
            mapping.put("actionOnCart", "ActionCartServlet");
            mapping.put("deleteCarFromCart", "RemoveCarFromCartServlet");
            mapping.put("renting", "RentingServlet");
            mapping.put("history", "HistoryServlet");
            mapping.put("historySearch", "SearchHistoryServlet");
            mapping.put("deleteHis", "DeleteHistoryServlet");
            mapping.put("loginPage", "login.jsp");
            mapping.put("signUpPage", "signup.jsp");
            mapping.put("confirmEmailPage", "confirm_email.jsp");
            mapping.put("confirm", "ConfirmServlet");
            mapping.put("rentingDate", "renting_car.jsp");
            mapping.put("viewCartPage", "viewcart.jsp");
        } catch (Exception e) {
            //------------------------------
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("FilterDispatcher()");
        }
        StringBuffer sb = new StringBuffer("FilterDispatcher(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
