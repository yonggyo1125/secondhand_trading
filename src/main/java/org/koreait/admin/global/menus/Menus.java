package org.koreait.admin.global.menus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menus {
    private static Map<String, List<Menu>> menus = new HashMap<>();

    static {
        // 사이트 설정 메뉴
        menus.put("config", List.of(
                new Menu("basic", "기본설정", "/admin/config"),
                new Menu("terms", "약관설정", "/admin/config/terms")
        ));

        // 회원 관리 메뉴
        menus.put("member", List.of(
                new Menu("list", "회원목록", "/admin/member")
        ));

        // 게시판 관리 메뉴
        menus.put("board", List.of(
           new Menu("list", "게시판 목록", "/admin/board"),
           new Menu("register", "게시판 등록", "/admin/board/register"),
           new Menu("posts", "게시글 관리", "/admin/board/posts")
        ));

        // 트렌드 관리 메뉴
        menus.put("trend", List.of(
           new Menu("news", "뉴스", "/admin/trend"),
           new Menu("etc", "기타", "/admin/trend/etc")
        ));

        // 상품 관리 메뉴
        menus.put("product", List.of(
                new Menu("list", "상품목록", "/admin/product"),
                new Menu("register", "상품등록", "/admin/product/register"),
                new Menu("category", "분류관리", "/admin/product/category")
        ));
    }

    /**
     * 주 메뉴 코드로 서브 메뉴를 조회
     *
     * @param mainCode
     * @return
     */
    public static List<Menu> getMenus(String mainCode) {
        return menus.getOrDefault(mainCode, List.of());
    }
}
