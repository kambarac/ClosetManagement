

import java.io.IOException;
import java.nio.file.Paths;

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

/**
 * 服飾情報追加サーブレット
 */
@WebServlet("/GearInfoAdd")
@MultipartConfig (  maxFileSize=1000000,
					maxRequestSize=1000000,
					fileSizeThreshold=1000000
					)
public class GearInfoAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GearInfoAdd() {
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

		// 画像保存処理
		Part part = request.getPart("g_image");
		//String file = part.getSubmittedFileName();//ie対応が不要な場合
		String filename = null;

		// 画像ファイル選択時処理
		if(part.getSize() != 0) {
			filename=Paths.get(part.getSubmittedFileName()).getFileName().toString();

			// アップロード先指定(WebContent下のimagefileフォルダ)
			String path = "C:\\java_2019\\pleiades\\workspace\\ClosetManagement\\WebContent\\imagefile\\";
			part.write(path + filename);
		}

		// フォーム入力データ取得
		String type = checkEmpty(request.getParameter("g_type"));
		String color = checkEmpty(request.getParameter("g_color"));
		String makers = checkEmpty(request.getParameter("g_makers"));

		// ブランド名のシングルクォーテーション処理
		String checked = "";
		for(int i = 0;i < makers.length();i++) {
			if(makers.charAt(i) == '\'') {
				checked += "'";
			}
			checked += makers.charAt(i);
		}

		// データ挿入用SQL文作成
		String sql = "INSERT INTO closet" + u_id + "(type,color,makers,image,date_create,date_update) values (";
		sql += "'" + type + "',";
		sql += "'" + color + "',";
		sql += "'" + checked + "',";
		sql += "'" + filename + "',";
		sql += "current_timestamp,";
		sql += "current_timestamp)";

		// SQL文実行
		GearData gearData = new GearData();
		gearData.setData(sql);
		System.out.println(sql);

		// ユーザー画面遷移
		ServletContext context = this.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/add_info.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// nullチェック
	public String checkEmpty(String str) {
		str = str =="" ? str = null : str.trim();
		return str;
	}

}
