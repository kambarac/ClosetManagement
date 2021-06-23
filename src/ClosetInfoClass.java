import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ClosetInfo;
import dto.ClosetData;

public class ClosetInfoClass {

	// クロゼット情報取得用クラス
	public ClosetInfoClass(){}

	public void processing(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		// セッションからユーザーIDを取得
		String u_id = (String)session.getAttribute("id");

		int recordCount = 0;

		// 種類とレコード数を格納するデータ
		List<ClosetData> type = new ArrayList<ClosetData>();
		List<ClosetData> color = new ArrayList<ClosetData>();
		List<ClosetData> makers = new ArrayList<ClosetData>();

		// 全レコード数
		String sql_all = "SELECT COUNT(id) FROM closet" + u_id;

		// 各項目種類別データ数
		String sql_type = "SELECT type,COUNT(type) FROM closet" + u_id + " GROUP BY type order by count desc";
		String sql_color = "SELECT color,COUNT(color) FROM closet" + u_id + " GROUP BY color order by count desc";
		String sql_makers = "SELECT makers,COUNT(makers) FROM closet" + u_id + " GROUP BY makers order by count desc";


		// 全服データ数
		ClosetInfo closetInfo = new ClosetInfo();
		recordCount = closetInfo.getRecordCount(sql_all);
		if(recordCount != -1){
			request.setAttribute("recordcount", String.valueOf(recordCount)); // DB内の全服飾数を送信
		}
		else {
			request.setAttribute("recordcount", "0"); // 登録データがない時
		}

		// 各項目数
		type = closetInfo.getData(sql_type, "type");
		request.setAttribute("typeCount", type);
		color = closetInfo.getData(sql_color, "color");
		request.setAttribute("colorCount", color);
		makers = closetInfo.getData(sql_makers, "makers");
		request.setAttribute("makersCount", makers);
	}
}
