package music.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class CharacterEncodingFilter
 */
public class CharacterEncodingFilter implements Filter {

	protected String encoding = "";
    /**
     * Phương pháp xây dựng
     */
    public CharacterEncodingFilter() {
    }

	/**
		 * Kết thúc
	*/
	public void destroy() {
		encoding = null;
	}


	/**
	* Đặt mã hóa ký tự được yêu cầu thành mã hóa trong bộ lọc
	*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (encoding != null){
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);
	}

	/**
	* khởi tạo
	*/
	public void init(FilterConfig fConfig) throws ServletException {
		this.encoding = fConfig.getInitParameter("encoding");
	}
}
