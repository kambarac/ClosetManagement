

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CsvWriter;

/**
 * CSV入力サーブレット
 */
@WebServlet("/CsvWriterServlet")
public class CsvWriterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CsvWriterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// セッションからユーザーIDとユーザーnameを取得
		String u_id = (String)session.getAttribute("id");
		String u_name = (String)session.getAttribute("name");

		// csv保存用フォルダの作成
		File folder = new File("C:/csv");
		if(folder.exists() == false) {
			folder.mkdir();
		}

		// csv保存用ファイルの作成
		String filename = "C:/csv/"+ u_name + "closet.csv";
		File file = new File(filename);
		if(file. exists() == false) {
			file.createNewFile();
		}

		// ファイル書き込み用SQL文作成
		String sql = "COPY closet" + u_id + " TO '" + filename + "' WITH CSV DELIMITER ',' encoding 'SJIS'";

		// ファイルへの書き込み
		CsvWriter csv = new CsvWriter();
		String result = csv.csvWrite(sql); // 書き込み結果を「OK」「error」で返す
		// 書き込み結果を格納
		request.setAttribute("result", result);

		// クロゼット情報を再取得
		ClosetInfoClass c_class = new ClosetInfoClass();
		c_class.processing(request, response);

		// クロゼット情報画面へ遷移
		ServletContext context = this.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/closet_info.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
