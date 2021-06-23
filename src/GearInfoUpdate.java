

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.GearData;
import dto.GearTable;

/**
 * 服飾データ変更サーブレット
 */
@WebServlet("/GearInfoUpdate")
@MultipartConfig (  maxFileSize=1000000,
maxRequestSize=1000000,
fileSizeThreshold=1000000)
public class GearInfoUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GearInfoUpdate() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// セッションからユーザーIDを取得
		String u_id = (String)session.getAttribute("id");

		// セッションから現在ページ数取得、データ取得件数用offset文作成
		int pageNum = (int)session.getAttribute("nowPage");
		String offset = " offset " + String.valueOf((pageNum - 1) * 12) + " limit 12";

		Part part = request.getPart("g_image");
		String filename = null;

		// データ変更用SQL文作成
		String sql = "UPDATE closet" + u_id + " SET ";

		if(part.getSize() != 0){
			// ファイル名を取得後、変数filenameに代入
			filename=Paths.get(part.getSubmittedFileName()).getFileName().toString();

			// アップロード先指定(WebContent下のimagefileフォルダ)
			String path = "C:\\java_2019\\pleiades\\workspace\\ClosetManagement\\WebContent\\imagefile\\";
			part.write(path + filename);
			sql += "image = '" + filename + "',";
		}

		String id = checkEmpty(String.valueOf(request.getParameter("g_id")));
		String type = checkEmpty(request.getParameter("g_type"));
		String color = checkEmpty(request.getParameter("g_color"));
		String makers = checkEmpty(request.getParameter("g_makers"));

		sql += "type = '" + type + "',";
		sql += "color = '" + color + "',";
		sql += "makers = '" + makers + "',";
		sql += "date_update = current_timestamp ";
		sql += "WHERE id =" + id;

		// SQL文実行
		GearData gearData = new GearData();
		gearData.setData(sql);


		// DB内の全服飾データを格納
		List<GearTable> closet = new ArrayList<GearTable>();
		sql = "SELECT id, type, color, makers, image, date_create, date_update FROM closet" + u_id + " ORDER BY id desc" + offset;
		closet = gearData.getTable(sql);
		request.setAttribute("closet", closet);

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


	public String checkEmpty(String str) {
		str = str =="" ? str = null : str.trim();
		return str;
	}

}
