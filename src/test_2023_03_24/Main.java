package test_2023_03_24;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static List<Article> articles = new ArrayList<>();
	static List<Member> members = new ArrayList<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		makeTestData();
		makeMemberTestData();
		Member loginedMember = null;

		int hitCount = 0;
		int lastId = 3;
		int lastMemberId = 3;
		System.out.println("==프로그램 시작==");
		while (true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine();

			if (cmd.equals("exit")) {
				break;
			} else if (cmd.startsWith("article modify ")) {
				if (loginedMember != null) {
					System.out.println("로그인 후 이용해주세요.");
					continue;
				}
				String[] cmdDiv = cmd.split(" ");
				int id = Integer.parseInt(cmdDiv[2]);

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.id == id) {
						foundArticle = article;
					}

				}
				if (foundArticle == null) {
					System.out.println("게시글이 없습니다");
					continue;
				}
				if (foundArticle.memberId != loginedMember.id) {
					System.out.println("권한이 없습니다.");
					continue;
				}

				System.out.println("새 제목 : ");
				String newTitle = sc.nextLine();
				System.out.println("새 내용 : ");
				String newBody = sc.nextLine();
				String updateDate = Util.getNowDateTimeStr();

				foundArticle.updateDate = updateDate;
				foundArticle.title = newTitle;
				foundArticle.body = newBody;

			}

			else if (cmd.equals("member join")) {

				if (loginedMember != null) {
					System.out.println("로그인 후 이용해주세요.");
					continue;
				}
				int id = lastMemberId + 1;
				String loginId = null;
				String loginPw = null;
				String loginPw1 = null;

				while (true) {
					System.out.print("로그인 아이디 : ");
					loginId = sc.nextLine();
					if (isJoinableLoginId(loginId) == false) {
						System.out.println("이미 사용중인 아이디입니다.");
						continue;
					}
					break;

				}

				while (true) {
					System.out.print("로그인 비밀번호 : ");
					loginPw = sc.nextLine();
					System.out.print("로그인 비밀번호 확인 : ");
					loginPw1 = sc.nextLine();

					if (loginPw.equals(loginPw1) == false) {

						System.out.println("비밀번호를 확인해주세요");
						continue;
					}
					break;
				}
				System.out.print("이름 : ");
				String name = sc.nextLine();
				String regDate = Util.getNowDateTimeStr();

				Member member = new Member(id, regDate, loginId, loginPw, name);
				members.add(member);
				System.out.println(id + "번 회원이 가입되었습니다.");
				lastMemberId++;
			}

			else if (cmd.equals("member login")) {
				if (loginedMember != null) {
					System.out.println("로그아웃 후 이용해주세요.");
					continue;
				}
				System.out.print("로그인 아이디 : ");
				String loginId = sc.nextLine();
				System.out.print("비밀번호 : ");
				String loginPw = sc.nextLine();

				Member member = getMemberByLoginId(loginId);

				if (member == null) {
					System.out.println("일치하는 회원이 없습니다");
					continue;
				}
				if (member.loginPw.equals(loginPw) == false) {
					System.out.println("비밀번호가 일치하지 않습니다.");
					continue;

				}

				System.out.println("로그인 성공!" + member.name + "님 반갑습니다.");
				loginedMember = member;
			}

			else if (cmd.equals("member logout")) {
				if (loginedMember == null) {
					System.out.println("로그인 후 이용해주세요");
					continue;
				} else {
					loginedMember = null;
					System.out.println("로그아웃 되었습니다.");
				}
			}

			else if (cmd.startsWith("article delete ")) {

				if (loginedMember == null) {
					System.out.println("로그인 후 이용해주세요");
					continue;
				}

				String[] cmdDiv = cmd.split(" ");
				int id = Integer.parseInt(cmdDiv[2]);

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);

					if (article.id == id) {
						foundArticle = article;
					}

				}
				if (foundArticle == null) {
					System.out.println("게시글이 없습니다");
					continue;
				}
				if (foundArticle.memberId != loginedMember.id) {
					System.out.println("권한이 없습니다.");
					continue;
				}
				articles.remove(foundArticle);
				System.out.println(id + "번 글을 삭제했습니다.");
			}

			else if (cmd.startsWith("article detail ")) {

				if (loginedMember == null) {
					System.out.println("로그인 후 이용해주세요");
					continue;
				}

				String[] cmdDiv = cmd.split(" ");
				int id = Integer.parseInt(cmdDiv[2]);
				int hit = hitCount + 1;
				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.id == id) {
						foundArticle = article;
					}

				}
				if (foundArticle == null) {
					System.out.println("게시글이 없습니다");
					continue;
				}

				System.out.println("번호 : " + foundArticle.id);
				System.out.println("작성날짜 : " + foundArticle.regDate);
				System.out.println("수정날짜 : " + foundArticle.updateDate);
				System.out.println("수정날짜 : " + foundArticle.name);
				System.out.println("제목 : " + foundArticle.title);
				System.out.println("내용 : " + foundArticle.body);
				System.out.println("조회 : " + foundArticle.hit);
				hitCount++;
			}

			else if (cmd.equals("member login")) {
				if (loginedMember != null) {
					System.out.println("로그아웃 후 이용해주세요");
					continue;
				}
				System.out.print("로그인 아이디 : ");
				String loginId = sc.nextLine();
				System.out.print("비밀번호 : ");
				String loginPw = sc.nextLine();

				Member member = getMemberByLoginId(loginId);

				if (member == null) {
					System.out.println("일치하는 회원이 없습니다");
					continue;
				}
				if (member.loginPw.equals(loginPw) == false) {
					System.out.println("비밀번호가 일치하지 않습니다.");
					continue;

				}

				System.out.println("로그인 성공!" + member.name + "님 반갑습니다");
				loginedMember = member;
			}

			else if (cmd.equals("member logout")) {
				if (loginedMember == null) {
					System.out.println("로그인 후 이용해주세요.");
					continue;
				} else {
					loginedMember = null;
					System.out.println("로그아웃 되었습니다.");
				}
			} else if (cmd.equals("member join")) {

				if (loginedMember != null) {
					System.out.println("로그아웃 상태가 아닙니다.");
					continue;
				}
				int id = lastMemberId + 1;
				String loginId = null;
				String loginPw = null;
				String loginPw1 = null;

				while (true) {
					System.out.print("아이디 : ");
					loginId = sc.nextLine();
					if (isJoinableLoginId(loginId) == false) {
						System.out.println("이미 사용중인 아이디입니다.");
						continue;
					}
					break;

				}

				while (true) {
					System.out.print("비밀번호 : ");
					loginPw = sc.nextLine();
					System.out.print("비밀번호 확인 : ");
					loginPw1 = sc.nextLine();

					if (loginPw.equals(loginPw1) == false) {

						System.out.println("비밀번호가 일치하지 않습니다");
						continue;
					}
					break;
				}
				System.out.print("이름 : ");
				String name = sc.nextLine();
				String regDate = Util.getNowDateTimeStr();

				Member member = new Member(id, regDate, loginId, loginPw, name);
				members.add(member);
				System.out.println(name + " 회원님 환영합니다");
				lastMemberId++;
			}

			else if (cmd.equals("article list"))

			{

				if (articles.size() == 0) {
					System.out.println("게시물이 존재하지 않습니다");
					continue;

				}
				System.out.println("번호   //   제목   //   조회   //   작성자");

				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);

					System.out.println(article.id + "   //   " + article.title + "   //   " + article.hit + "   //    "
							+ article.name);
				}

			} else if (cmd.equals("article write")) {
				if (loginedMember == null) {
					System.out.println("로그인 후 이용해주세요.");
					continue;
				}
				int id = lastId + 1;
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();
				String regDate = Util.getNowDateTimeStr();
				String updateDate = Util.getNowDateTimeStr();

				Article article = new Article(id, regDate, updateDate, title, body, loginedMember.name);
				articles.add(article);

				System.out.println(id + "번글이 생성되었습니다.");
				lastId++;
			}

			else {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

		}

		System.out.println("==프로그램 종료==");
		sc.close();
	}

	private static Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return null;
		}

		return members.get(index);
	}

	private static boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return true;
		}

		return false;
	}

	private static int getMemberIndexByLoginId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	private static void makeMemberTestData() {

		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");
		members.add(new Member(1, Util.getNowDateTimeStr(), "test1", "test1", "김철수"));
		members.add(new Member(2, Util.getNowDateTimeStr(), "test2", "test2", "김영희"));
		members.add(new Member(2, Util.getNowDateTimeStr(), "test3", "test3", "김영희"));

	}

	private static void makeTestData() {
		System.out.println("테스트를 위한 게시글 데이터를 생성합니다.");
		articles.add(new Article(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), 1, "제목1", "내용1", 11, "홍길동"));
		articles.add(new Article(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), 2, "제목2", "내용2", 22, "김영희"));
		articles.add(new Article(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), 3, "제목3", "내용3", 33, "김영희"));

	}
}

class Article {

	int id;
	String regDate;
	String updateDate;
	String title;
	String body;
	int hit;
	String name;
	int memberId;

	public Article(int id, String regDate, String updateDate, String title, String body, String name) {

		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
		this.name = name;

	}

	public Article(int id, String regDate, String updateDate, int memberId, String title, String body, int hit,
			String name) {

		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.memberId = memberId;
		this.title = title;
		this.body = body;
		this.hit = hit;
		this.name = name;

	}

}

class Member {

	int id;
	String regDate;
	String loginId;
	String loginPw;
	String name;

	public Member(int id, String regDate, String loginId, String loginPw, String name) {

		this.id = id;
		this.regDate = regDate;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
	}

}