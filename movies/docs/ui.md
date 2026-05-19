# UI Request
1. User Create (id, name, grade)
   1. 여기서 User를 생성할 수 있는 화면을 왼쪽에 배치
   2. Usser 등록, 수정, 삭제, 검색 등의 service를 UI에서 사용할 수 있게 버튼 배치
   3. admin User를 따로 생성 특정번호로 ex) id : 9999 name : admin grad : admin
2. Movie Create (movie_id, movie_name, air_date, hall_id)
   1. 여기서 Movie를 생성할 수 있는 화면을 유저화면 왼쪽 하단에 배치
   2. User를 검색했을 경우 만약 검색 결과가 Admin일 시 영화 등록, 수정, 삭제, 검색이 가능하도록
   3. 일반 유저일 경우에는 검색만 가능하도록
   4. 기본적으로 SearchAll로 영화 목록이 보이도록
   5. 또한 영화 MOCK데이터를 추가
3. Reservation Create (reservation_id, sit_id, user_id(foreign_key), movie_id(foreign_key))
   1. 여기서 User는 우측 검색 결과 영화 목록을 보고 해당 영화를 선택 시 예약 Dialog 창을 띄우도록 제작
   2. 해당 Dialog UI에는 좌석 정보가 보이고 해당 좌석 버튼을 클릭시 예약하시겠습니까? YES/NO 버튼이 생성되고 YES 클릭 시 예약 행 생성