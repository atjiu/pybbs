package cn.weibo.util;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.BitSet;
/**
 * @author sinaWeibo
 * 
 */
public class URLEncodeUtils {

	static BitSet dontNeedEncoding;

	static {

		/*
		 * The list of characters that are not encoded has been determined as
		 * follows:
		 * 
		 * RFC 2396 states: ----- Data characters that are allowed in a URI but
		 * do not have a reserved purpose are called unreserved. These include
		 * upper and lower case letters, decimal digits, and a limited set of
		 * punctuation marks and symbols.
		 * 
		 * unreserved = alphanum | mark
		 * 
		 * mark = "-" | "_" | "." | "!" | "~" | "*" | "'" | "(" | ")"
		 * 
		 * Unreserved characters can be escaped without changing the semantics
		 * of the URI, but this should not be done unless the URI is being used
		 * in a context that does not allow the unescaped character to appear.
		 * -----
		 * 
		 * It appears that both Netscape and Internet Explorer escape all
		 * special characters from this list with the exception of "-", "_",
		 * ".", "*". While it is not clear why they are escaping the other
		 * characters, perhaps it is safest to assume that there might be
		 * contexts in which the others are unsafe if not escaped. Therefore, we
		 * will use the same list. It is also noteworthy that this is consistent
		 * with O'Reilly's "HTML: The Definitive Guide" (page 164).
		 * 
		 * As a last note, Intenet Explorer does not encode the "@" character
		 * which is clearly not unreserved according to the RFC. We are being
		 * consistent with the RFC in this matter, as is Netscape.
		 */

		dontNeedEncoding = new BitSet(256);
		int i;
		for (i = 'a'; i <= 'z'; i++) {
			dontNeedEncoding.set(i);
		}
		for (i = 'A'; i <= 'Z'; i++) {
			dontNeedEncoding.set(i);
		}
		for (i = '0'; i <= '9'; i++) {
			dontNeedEncoding.set(i);
		}
		dontNeedEncoding.set(' '); /*
									 * encoding a space to a + is done in the
									 * encode() method
									 */
		dontNeedEncoding.set('-');
		dontNeedEncoding.set('_');
		dontNeedEncoding.set('.');
		dontNeedEncoding.set('*');

		dontNeedEncoding.set('+');
		dontNeedEncoding.set('%');

	}

	/**
	 * 判断段落文本是否被urlencode过
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean isURLEncoded(String str) {
		if (str==null||"".equals(str)) {
			return false;
		}
		char[] chars = str.toCharArray();
		boolean containsPercent = false;
		for (char c : chars) {
			if (Character.isWhitespace(c)) {
				return false;
			}
			if (!dontNeedEncoding.get(c)) {
				return false;
			}
			if(c == '%'){
				containsPercent = true;
			}
		}
		if(!containsPercent){
			return false;
		}
		return true;
	}

	public static final String encodeURL(String str) {
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	public static final String decodeURL(String str) {
		try {
			return URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
