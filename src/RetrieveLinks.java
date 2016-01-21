import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RetrieveLinks {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<String> retrieveLinks(String pageContents) {
			  // Compile link matching pattern.
			  Pattern p =
			    Pattern.compile("<a\\s+href\\s*=\\s*\"?(.*?)[\"|>]", Pattern.CASE_INSENSITIVE);
			  Matcher m = p.matcher(pageContents);
			  // Create list of link matches.
			  ArrayList<String> tmpLinkList = new ArrayList();
			  while (m.find()) {
			    String link = m.group(1).trim().toLowerCase();
			    tmpLinkList.add(link);
			  }
			  return (tmpLinkList);
			}
	
	public static String validateLink(URL pageUrl, String link, ArrayList<String> linkList,
			HashSet<String> crawledList, boolean limitHost) throws MalformedURLException, IOException
{
				while (link.charAt(0) == '.')
					link = link.substring(1);
			    // Skip empty links.
			    if (link.length() <= 1)
			    	return "";
			    // Skip links that are just page anchors.
			    if (link.charAt(0) == '#')
			    	return "";
			    // Skip mailto links.
			    if (link.indexOf("mailto:") != -1)
			    	return "";
			    // Skip JavaScript links.
			    if (link.toLowerCase().indexOf("javascript") != -1)
			    	return "";
			    // Prefix absolute and relative URLs if necessary.
			    if (link.indexOf("://") == -1) {
			      // Handle absolute URLs.
			      if (link.charAt(0) == '/') {
			        link = "http://" + pageUrl.getHost() + link;
			      // Handle relative URLs.
			      } else {
			        String file = pageUrl.getFile();
			        if (file.indexOf('/') == -1) {
			          link = "http://" + pageUrl.getHost() + "/" + link;
			        } else {
			          String path =
			            file.substring(0, file.lastIndexOf('/') + 1); 
			          link = "http://" + pageUrl.getHost() + path + link;
			        }
			      }
			    }
			    // Remove anchors from link.
			    int index = link.indexOf('#');
			    if (index != -1) {
			      link = link.substring(0, index);
			    }
			    // Skip link if it is already in link's list.
			    if (linkList.contains(link))
			    	return "";
			    // Skip link if it has already been crawled.
			    if (crawledList.contains(link))
			    	return "";
			    // Verify link and skip if invalid.
			    URL verifiedLink = verifyUrl(link);
			    if (verifiedLink == null) {
			    	return "";
			    }
			    // Verify link response and skip if invalid.
			    if (!validSite(link))
			    	return "";
			    // Verify mime type and skip if invalid.
			    if (!validMimeType(link)) {
			    	return "";
			    }
			    /* If specified, limit links to those
			      having the same host as the start URL. */
			    if (limitHost &&
			        !pageUrl.getHost().toLowerCase().equals(
			          verifiedLink.getHost().toLowerCase()))  
			    {
			    	return "";
			    }
			    
			  return link;
			}
	
	private static URL verifyUrl(String url) {
		  // Only allow HTTP URLs.
		  if (!url.toLowerCase().startsWith("http://"))
		    return null;
		  // Verify format of URL.
		  URL verifiedUrl = null;
		  try {
		    verifiedUrl = new URL(url);
		  } catch (Exception e) {
		    return null;
		  }
		  return verifiedUrl;
		}
	
	public static int getResponseCode(String urlString) throws MalformedURLException, IOException {
	    URL u = new URL(urlString); 
	    HttpURLConnection huc =  (HttpURLConnection)  u.openConnection(); 
	    huc.setRequestMethod("GET"); 
	    try{
	    	huc.connect();
	    } catch (ConnectException e){
	    	return -1;
	    }
	    return huc.getResponseCode();
	}
	
	public static boolean validSite (String urlString) {
    	int respCode = 0;
			try {
				respCode = getResponseCode(urlString);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (respCode != 200) {
				return false;
			}
    	return true;
    }
	
	public static boolean validMimeType(String urlString) throws java.io.IOException, MalformedURLException {
	   	String type = null;
	   	URL u = new URL(urlString);
	   	URLConnection uc = null;
	   	uc = u.openConnection();
	   	type = uc.getContentType();
	   	if (type.isEmpty() || type == null || !type.startsWith("text/"))
	   		return false;
	   	
	   	return true;
	}
}