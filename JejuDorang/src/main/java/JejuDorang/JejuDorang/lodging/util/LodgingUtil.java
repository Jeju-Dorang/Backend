package JejuDorang.JejuDorang.lodging.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import JejuDorang.JejuDorang.lodging.enums.LodgingDirection;

public class LodgingUtil {

	public static LodgingDirection getDirectionByAddress(String address) {
		// 지역별 방향 매핑
		Map<String, LodgingDirection> regionDirectionMap = new HashMap<>();
		// 서쪽 지역
		regionDirectionMap.put("한경", LodgingDirection.WEST);
		regionDirectionMap.put("한림", LodgingDirection.WEST);
		regionDirectionMap.put("대정", LodgingDirection.WEST);
		regionDirectionMap.put("안덕", LodgingDirection.WEST);
		// 동쪽 지역
		regionDirectionMap.put("구좌", LodgingDirection.EAST);
		regionDirectionMap.put("표선", LodgingDirection.EAST);
		regionDirectionMap.put("성산", LodgingDirection.EAST);
		// 북쪽 지역
		regionDirectionMap.put("애월", LodgingDirection.NORTH);
		regionDirectionMap.put("제주시", LodgingDirection.NORTH);
		regionDirectionMap.put("조천", LodgingDirection.NORTH);
		// 남쪽 지역
		regionDirectionMap.put("중문", LodgingDirection.SOUTH);
		regionDirectionMap.put("서귀포시", LodgingDirection.SOUTH);
		regionDirectionMap.put("남원", LodgingDirection.SOUTH);

		// 주소에서 지역명 추출
		String region = extractRegionFromAddress(address);

		// 지역명으로 방향 결정
		return regionDirectionMap.getOrDefault(region, LodgingDirection.UNKNOWN);
	}

	private static String extractRegionFromAddress(String address) {
		// 주소를 공백이나 쉼표로 분할하여 각 부분을 배열에 저장
		String[] addressParts = address.split("\\s+|,");
		for (String part : addressParts) {
			// 지역명이 매핑 테이블에 있는지 확인
			if (isRegionName(part)) {
				return part;
			}
		}
		return null;
	}

	private static boolean isRegionName(String name) {
		Set<String> regionNames = new HashSet<>(Arrays.asList(
			"한경", "한림", "대정", "안덕",
			"구좌", "표선", "성산",
			"애월", "제주시", "조천",
			"중문", "서귀포시", "남원"
		));
		return regionNames.contains(name);
	}

}
