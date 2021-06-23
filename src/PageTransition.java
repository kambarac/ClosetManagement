

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GearData;
import dto.GearTable;

/**
 * ページング処理サーブレット
 */
@WebServlet("/PageTransition")
public class PageTransition extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PageTransition() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// セッションからユーザーID、現在ページ数を取得
		String u_id = (String)session.getAttribute("id");
		int pageNum = (int)session.getAttribute("nowPage");

		// ページング処理
		// 数字ボタンの時
		if(request.getParameter("bAa") == null) {
			pageNum = Integer.valueOf(request.getParameter("page"));
		}
		// 前後ボタンの時
		if(request.getParameter("page") == null) {
			String baa = String.valueOf(request.getParameter("bAa"));
			System.out.println(session.getAttribute("nowPage"));
			String before = "before";
			String next = "next";
			if(baa.equals(before)) {
				pageNum -= 1;
			}
			if(baa.equals(next)) {
				pageNum += 1;
			}
		}

		// SQL文の作成
		String offset = " offset " + String.valueOf((pageNum - 1) * 12) + " limit 12";
		String sql ="";
		if(session.getAttribute("where") == null) {
			sql = "SELECT id, type, color, makers, image, date_create, date_update FROM closet" + u_id + " ORDER BY id desc" + offset;
		}
		else {
			sql = "SELECT id, type, color, makers, image, date_create, date_update FROM closet" + u_id + session.getAttribute("where") + " ORDER BY id desc" + offset;
		}

		// SQL文の実行
		GearData gearData = new GearData();
		List<GearTable> closet = new ArrayList<GearTable>();
		closet = gearData.getTable(sql);
		// DB内の全服飾データを格納
		request.setAttribute("closet", closet);

		// セッションに遷移後表示ページ数を格納
		session.setAttribute("nowPage", pageNum);

		// ユーザー画面遷移
		ServletContext context = this.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/userpage.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
