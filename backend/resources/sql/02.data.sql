-- MySQL dump 10.13  Distrib 8.0.33, for macos13 (arm64)
--
-- Host: 127.0.0.1    Database: whereismyhome
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- -----------------------------------------------------
-- Schema whereismyhome
-- -----------------------------------------------------
USE `whereismyhome`;


-- -----------------------------------------------------
-- Table `whereismyhome`.`member`
-- -----------------------------------------------------
INSERT INTO `member` (emailAccount, emailDomain, password, name, age, sex, authorization)
VALUES ('cheesecat47', 'gmail.com', '1234', '신주용', 20, 'M', 1),
       ('dltmdgus4802', 'gmail.com', '1234', '이승현', 20, 'M', 1)
;
set @memberId_cheesecat47 = (select memberId from member where emailAccount = 'cheesecat47');
set @memberId_dltmdgus4802 = (select memberId from member where emailAccount = 'dltmdgus4802');

-- -----------------------------------------------------
-- Table `whereismyhome`.`member_address`
-- -----------------------------------------------------
set @dongCode_doryang = (select dongCode from `whereismyhome`.`dongcode` where gugunName = '구미시' and dongName like '도량%' limit 1);
set @dongCode_jinpyung = (select dongCode from `whereismyhome`.`dongcode` where gugunName = '구미시' and dongName like '진평%' limit 1);
set @dongCode_gupyung = (select dongCode from `whereismyhome`.`dongcode` where gugunName = '구미시' and dongName like '구평%' limit 1);
set @dongCode_inyi = (select dongCode from `whereismyhome`.`dongcode` where gugunName = '구미시' and dongName like '인의%' limit 1);
set @dongCode_hwangsang = (select dongCode from `whereismyhome`.`dongcode` where gugunName = '구미시' and dongName like '황상%' limit 1);
INSERT INTO `member_address` (memberId, address, dongCode)
VALUES
    (@memberId_cheesecat47, '경상북도 구미시 도량동', @dongCode_doryang),
    (@memberId_dltmdgus4802, '경상북도 구미시 진평동', @dongCode_jinpyung)
;


-- -----------------------------------------------------
-- Table `whereismyhome`.`board`
-- 공지사항 예시 출처 https://www.bdsplanet.com/cs/notice.ytp
-- -----------------------------------------------------
INSERT INTO `board` (title, content, type, memberId, dongCode, hit, createdTime)
VALUES
    ('낙동강 산책', '낙동강 체육공원 천둥 오리도 벌써 왔네요', 0, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, now() - 14),
    ('라면축제에왔어요', '농심에서 라끼남 강호동출현 ㅎㅎ', 0, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, now() - 13),
    ('짐옮길려고 하는데 용달추천 부탁드려용', '급하게 찾아보아여 아시는 곳이나 저렴한 곳이나 추천부탁드려욤', 0, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, now() - 12),
    ('남자 머리 커트', '머리 싸게 하는곳 추천 부탁드립니다.', 0, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, now() - 11),
    ('라면축제 저도구경', '제2회라면축제라네요.\n첨 왔는데 사람도 넘 많고\n바람도 넘 마니 부네요.', 0, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, now() - 10),
    ('원평동 붕어빵', '붕어빵 파는 곳 좀 알려주세요 ~', 0, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, now() - 9),
    ('롤드컵', '식당에서 롤드컵 보고싶은데 틀어주는 음식점 없겠죠...', 0, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, now() - 8),
    ('피아노', '혹 피아노 어른왕초보 배울수있는데\n아시는분있나요?\n추천부탁드려요?', 0, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, now() - 7),
    ('중화요리 관심 있는분', '댓글 달아주세요', 0, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, now() - 6),
    ('황상동에서 신평동으로 이사짐요', '안녕하십니까\n황상동에서 신평동까지\n매트리스 한개입니다\n3만원에 해줄 주실분요\n엘베가 없습니다ㅠㅠ', 0, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, now() - 5),
    ('건조기를 사고싶은데 어떻게 운반하나요..??', '건조기 사고싶은데 혼자서 운반은 힘들거 같은데 좋은 방법이 있을가요..?? 차는 있는데 들어 갈지가 의문이네여 거래해 보신분 계시나요', 0, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, now() - 4),
    ('저녁먹고', '걷기운동하실남녀분들있으신가요?운동삼아해볼려고합니다', 0, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, now() - 3),
    ('도량1동 붕어빵 파는곳 아시는분', '도량1동이나 원호쪽에 붕어빵 파는곳 없나요?! 슈붕 너무 먹고싶은데ㅜㅜ', 0, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, now() - 2),
    ('토요일 배드민턴 벙🏸', '장소는 참석자들과 상의하겠습니다', 0, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, now() - 1),
    ('휘닉스파크⛷️', '12월 첫째주 휘팍 가요😀', 0, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, now()),
    ('[완료] 데이터센터 장애로 인한 접속 불안정 안내 건', '안녕하세요.\n부동산 거래의 시작과 끝.\n\'부동산플래닛\'입니다.\n\n데이터센터 장애로 인하여 \n금일(08.06,일) AM 04:28 ~ AM 06:20까지 2시간동안 서버장애로 인해 접속이 되지 않았으나\n현재는 정상적으로 사용가능합니다.', 1, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, '2023-08-06'),
    ('실거래 데이터 08월 15일자 기준 업데이트 안내', '안녕하세요. \n\n부동산 거래의 시작과 끝.\n\'부동산플래닛\'입니다.\n\n언제 어디서든 전국 토지·건물 실거래가와 매물을\n한 번에 확인할 수 있는 \'실거래가 조회\' 서비스의\n실거래 데이터가 2023년 08월 15일자(국토교통부 데이터 공개일자) 기준으로\n반영되었습니다.', 1, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, '2023-08-17'),
    ('[공지] 부동산플래닛 \'간추린 뉴스\' 구독 서비스 변경 안내', '안녕하세요.\n\n부동산 거래의 시작과 끝,\n부동산플래닛입니다.\n\n부동산플래닛의 \'간추린 뉴스\' 구독 서비스를 이용해 주셔서 진심으로 감사드립니다.\n간추린 뉴스 서비스 품질 향상 및 효율적인 관리를 위해 변경 사항을 알려드립니다.\n\n아래 기준에 해당되는 회원님께서는\n2023년 08월 31일(목)까지 로그인하여 계정 활성화를 부탁드립니다.\n계정 활성화를 통해 계속해서 \'간추린 뉴스\'를 받아보실 수 있습니다.', 1, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, '2023-08-21'),
    ('[완료] 데이터센터 클라우드 네트워크 점검 안내', '안녕하세요?\n\n부동산 거래의 시작과 끝.\n\'부동산플래닛\'입니다.\n\n아래와 같이 데이터센터의 클라우드 네트워크장비 교체 및 점검 일정을 안내드립니다.', 1, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, '2023-08-22'),
    ('실거래 데이터 08월 31일자 기준 업데이트 안내', '안녕하세요. \n\n부동산 거래의 시작과 끝.\n\'부동산플래닛\'입니다.\n\n언제 어디서든 전국 토지·건물 실거래가와 매물을\n한 번에 확인할 수 있는 \'실거래가 조회\' 서비스의\n실거래 데이터가 2023년 08월 31일자(국토교통부 데이터 공개일자) 기준으로\n반영되었습니다.', 1, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, '2023-09-04'),
    ('[완료] 네트워크 접속 장애(디도스 공격) 관련 안내', '안녕하세요.\n\n부동산 거래의 시작과 끝,\n\'부동산플래닛\'입니다.\n\n일부 서버에 한하여 접속 장애가 발생하였습니다.\n현재는 조치 완료하여 정상적으로 서비스 이용이 가능합니다.', 1, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, '2023-09-11'),
    ('중개회원 관리비 세분화 입력 기능 관련 안내', '안녕하세요.\n\n부동산 거래의 시작과 끝,\n\'부동산플래닛\'입니다.\n"소규모 주택 관리비 투명화 방안"에 따라\n부동산플래닛의 매물등록 서비스가 일부 수정되었습니다.\n\n관리비 부과방식에 따라 3가지 유형(①정액 관리비, ②기타, ③확인 불가) 중 선택 입력이 가능합니다.', 1, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, '2023-09-13'),
    ('실거래 데이터 09월 15일자 기준 업데이트 안내', '안녕하세요. \n\n부동산 거래의 시작과 끝.\n\'부동산플래닛\'입니다.\n\n언제 어디서든 전국 토지·건물 실거래가와 매물을\n한 번에 확인할 수 있는 \'실거래가 조회\' 서비스의\n실거래 데이터가 2023년 09월 15일자(국토교통부 데이터 공개일자) 기준으로\n반영되었습니다.', 1, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, '2023-09-18'),
    ('9월 22일(금) 고객센터 단축 운영 안내드립니다.', '안녕하세요.\n\n부동산 거래의 시작과 끝,\n\'부동산플래닛\'입니다.\n\n9월 22일(금) 부동산플래닛 창립기념행사 일정으로 인해\n고객센터 전화상담이 단축 운영됩니다.', 1, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, '2023-09-18'),
    ('[완료]시스템 업데이트 및 서버작업 안내', '부동산플래닛에서 알려드립니다.\n\n보다 나은서비스를 제공해 드리기 위해 서비스 점검 작업이 진행될 예정입니다.\n작업 시간 중에는 이용에 불편이 있을수 있사오니, \n아래 점검 시간을 반드시 확인 후 이용해 주시기 바랍니다.', 1, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, '2023-09-21'),
    ('추석, 임시공휴일, 개천절 연휴 고객센터/간추린뉴스 운영 휴무 안내', '안녕하세요.\n\n부동산 거래의 시작과 끝,\n\'부동산플래닛\'입니다.\n\n추석, 임시공휴일, 개천절 연휴 기간\n고객센터 및 간추린뉴스 운영에 대해 다음과 같이 안내드립니다.', 1, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, '2023-09-26'),
    ('실거래 데이터 09월 30일자 기준 업데이트 안내', '안녕하세요. \n\n부동산 거래의 시작과 끝.\n\'부동산플래닛\'입니다.\n\n언제 어디서든 전국 토지·건물 실거래가와 매물을\n한 번에 확인할 수 있는 \'실거래가 조회\' 서비스의\n실거래 데이터가 2023년 09월 30일자(국토교통부 데이터 공개일자) 기준으로\n반영되었습니다.', 1, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, '2023-10-02'),
    ('실거래 데이터 10월 15일자 기준 업데이트 안내', '안녕하세요. \n\n부동산 거래의 시작과 끝.\n\'부동산플래닛\'입니다.\n\n언제 어디서든 전국 토지·건물 실거래가와 매물을\n한 번에 확인할 수 있는 \'실거래가 조회\' 서비스의\n실거래 데이터가 2023년 10월 15일자(국토교통부 데이터 공개일자) 기준으로\n반영되었습니다.', 1, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, '2023-10-17'),
    ('실거래 데이터 10월 31일자 기준 업데이트 안내', '안녕하세요. \n\n부동산 거래의 시작과 끝.\n\'부동산플래닛\'입니다.\n\n언제 어디서든 전국 토지·건물 실거래가와 매물을\n한 번에 확인할 수 있는 \'실거래가 조회\' 서비스의\n실거래 데이터가 2023년 10월 31일자(국토교통부 데이터 공개일자) 기준으로\n반영되었습니다.', 1, @memberId_cheesecat47, @dongCode_doryang, floor(rand() * 89) + 47, '2023-11-02'),
    ('실거래 데이터 11월 15일자 기준 업데이트 안내', '안녕하세요. \n\n부동산 거래의 시작과 끝.\n\'부동산플래닛\'입니다.\n\n언제 어디서든 전국 토지·건물 실거래가와 매물을\n한 번에 확인할 수 있는 \'실거래가 조회\' 서비스의\n실거래 데이터가 2023년 11월 15일자(국토교통부 데이터 공개일자) 기준으로\n반영되었습니다.', 1, @memberId_dltmdgus4802, @dongCode_jinpyung, floor(rand() * 89) + 47, '2023-11-17')
;

-- -----------------------------------------------------
-- Table `whereismyhome`.`dong_view_history`
-- -----------------------------------------------------
insert into `whereismyhome`.`dong_view_history`(memberId, dongCode, createdTime)
values
    (1,@dongCode_doryang,'2023-11-11'),(1,@dongCode_doryang,'2023-11-21'),(2,@dongCode_doryang,'2023-11-02'),(2,@dongCode_doryang,'2023-11-19'),(2,@dongCode_doryang,'2023-11-21'),
    (1,@dongCode_doryang,'2023-11-08'),(2,@dongCode_doryang,'2023-11-10'),(1,@dongCode_doryang,'2023-10-29'),(2,@dongCode_doryang,'2023-11-19'),(2,@dongCode_doryang,'2023-11-02'),
    (2,@dongCode_doryang,'2023-11-08'),(2,@dongCode_doryang,'2023-11-05'),(2,@dongCode_doryang,'2023-10-27'),(1,@dongCode_doryang,'2023-11-13'),(2,@dongCode_doryang,'2023-11-23'),
    (1,@dongCode_doryang,'2023-10-31'),(1,@dongCode_doryang,'2023-11-08'),(2,@dongCode_doryang,'2023-11-23'),(1,@dongCode_doryang,'2023-11-24'),(1,@dongCode_doryang,'2023-11-03'),
    (2,@dongCode_jinpyung,'2023-11-24'),(1,@dongCode_jinpyung,'2023-10-23'),(1,@dongCode_jinpyung,'2023-11-02'),(1,@dongCode_jinpyung,'2023-10-27'),(2,@dongCode_jinpyung,'2023-10-23'),
    (2,@dongCode_jinpyung,'2023-10-24'),(2,@dongCode_jinpyung,'2023-11-01'),(1,@dongCode_jinpyung,'2023-11-08'),(1,@dongCode_jinpyung,'2023-11-19'),(1,@dongCode_jinpyung,'2023-11-21'),
    (1,@dongCode_jinpyung,'2023-10-24'),(2,@dongCode_jinpyung,'2023-10-27'),(1,@dongCode_jinpyung,'2023-11-11'),(2,@dongCode_jinpyung,'2023-11-03'),(2,@dongCode_jinpyung,'2023-10-29'),
    (2,@dongCode_jinpyung,'2023-11-09'),(1,@dongCode_jinpyung,'2023-11-20'),(2,@dongCode_jinpyung,'2023-11-13'),(2,@dongCode_jinpyung,'2023-11-02'),(1,@dongCode_jinpyung,'2023-11-14'),
    (2,@dongCode_gupyung,'2023-11-12'),(1,@dongCode_gupyung,'2023-11-11'),(1,@dongCode_gupyung,'2023-11-13'),(2,@dongCode_gupyung,'2023-11-08'),(1,@dongCode_gupyung,'2023-11-01'),
    (1,@dongCode_gupyung,'2023-11-02'),(1,@dongCode_gupyung,'2023-11-09'),(2,@dongCode_gupyung,'2023-11-11'),(2,@dongCode_gupyung,'2023-10-25'),(1,@dongCode_gupyung,'2023-11-17'),
    (1,@dongCode_gupyung,'2023-10-29'),(2,@dongCode_gupyung,'2023-11-04'),(2,@dongCode_gupyung,'2023-10-23'),(1,@dongCode_gupyung,'2023-11-02'),(1,@dongCode_gupyung,'2023-11-10'),
    (2,@dongCode_gupyung,'2023-11-01'),(1,@dongCode_gupyung,'2023-11-16'),(1,@dongCode_gupyung,'2023-10-31'),(1,@dongCode_gupyung,'2023-10-31'),(2,@dongCode_gupyung,'2023-10-31'),
    (1,@dongCode_inyi,'2023-11-05'),(2,@dongCode_inyi,'2023-11-05'),(1,@dongCode_inyi,'2023-10-23'),(1,@dongCode_inyi,'2023-10-29'),(1,@dongCode_inyi,'2023-11-07'),
    (1,@dongCode_inyi,'2023-11-01'),(2,@dongCode_inyi,'2023-11-10'),(2,@dongCode_inyi,'2023-11-14'),(2,@dongCode_inyi,'2023-11-14'),(2,@dongCode_inyi,'2023-11-20'),
    (2,@dongCode_inyi,'2023-11-13'),(1,@dongCode_inyi,'2023-11-14'),(2,@dongCode_inyi,'2023-10-26'),(1,@dongCode_inyi,'2023-11-06'),(1,@dongCode_inyi,'2023-11-10'),
    (2,@dongCode_inyi,'2023-11-20'),(2,@dongCode_inyi,'2023-11-08'),(2,@dongCode_inyi,'2023-11-19'),(2,@dongCode_inyi,'2023-10-26'),(2,@dongCode_inyi,'2023-10-29'),
    (1,@dongCode_hwangsang,'2023-11-15'),(2,@dongCode_hwangsang,'2023-11-16'),(2,@dongCode_hwangsang,'2023-11-18'),(2,@dongCode_hwangsang,'2023-11-08'),(2,@dongCode_hwangsang,'2023-10-29'),
    (1,@dongCode_hwangsang,'2023-11-12'),(2,@dongCode_hwangsang,'2023-11-12'),(1,@dongCode_hwangsang,'2023-11-18'),(1,@dongCode_hwangsang,'2023-11-03'),(2,@dongCode_hwangsang,'2023-10-29'),
    (1,@dongCode_hwangsang,'2023-11-21'),(1,@dongCode_hwangsang,'2023-10-27'),(2,@dongCode_hwangsang,'2023-11-08'),(1,@dongCode_hwangsang,'2023-11-10'),(1,@dongCode_hwangsang,'2023-10-25'),
    (2,@dongCode_hwangsang,'2023-11-23'),(1,@dongCode_hwangsang,'2023-10-27'),(2,@dongCode_hwangsang,'2023-11-21'),(2,@dongCode_hwangsang,'2023-10-31'),(1,@dongCode_hwangsang,'2023-11-09');


-- -----------------------------------------------------
-- Table `whereismyhome`.`apt_view_history`
-- -----------------------------------------------------
set @aptCode_1 = (select aptCode from `whereismyhome`.`houseinfo` where dongCode = @dongCode_doryang order by aptCode limit 1 offset 1);
set @aptCode_2 = (select aptCode from `whereismyhome`.`houseinfo` where dongCode = @dongCode_doryang order by aptCode limit 1 offset 2);
set @aptCode_3 = (select aptCode from `whereismyhome`.`houseinfo` where dongCode = @dongCode_doryang order by aptCode limit 1 offset 3);
set @aptCode_4 = (select aptCode from `whereismyhome`.`houseinfo` where dongCode = @dongCode_doryang order by aptCode limit 1 offset 4);
set @aptCode_5 = (select aptCode from `whereismyhome`.`houseinfo` where dongCode = @dongCode_doryang order by aptCode limit 1 offset 5);
insert into `whereismyhome`.apt_view_history(memberId, aptCode, createdTime)
values
    (1,@aptCode_1,'2023-11-11'),(1,@aptCode_1,'2023-11-21'),(2,@aptCode_1,'2023-11-02'),(2,@aptCode_1,'2023-11-19'),(2,@aptCode_1,'2023-11-21'),
    (1,@aptCode_1,'2023-11-08'),(2,@aptCode_1,'2023-11-10'),(1,@aptCode_1,'2023-10-29'),(2,@aptCode_1,'2023-11-19'),(2,@aptCode_1,'2023-11-02'),
    (2,@aptCode_1,'2023-11-08'),(2,@aptCode_1,'2023-11-05'),(2,@aptCode_1,'2023-10-27'),(1,@aptCode_1,'2023-11-13'),(2,@aptCode_1,'2023-11-24'),
    (1,@aptCode_1,'2023-10-31'),(1,@aptCode_1,'2023-11-08'),(2,@aptCode_1,'2023-10-26'),(1,@aptCode_1,'2023-10-24'),(1,@aptCode_1,'2023-11-03'),
    (2,@aptCode_2,'2023-10-24'),(1,@aptCode_2,'2023-10-23'),(1,@aptCode_2,'2023-11-02'),(1,@aptCode_2,'2023-10-27'),(2,@aptCode_2,'2023-10-23'),
    (2,@aptCode_2,'2023-10-24'),(2,@aptCode_2,'2023-11-01'),(1,@aptCode_2,'2023-11-08'),(1,@aptCode_2,'2023-11-19'),(1,@aptCode_2,'2023-11-21'),
    (1,@aptCode_2,'2023-10-24'),(2,@aptCode_2,'2023-10-27'),(1,@aptCode_2,'2023-11-11'),(2,@aptCode_2,'2023-11-03'),(2,@aptCode_2,'2023-10-29'),
    (2,@aptCode_2,'2023-11-09'),(1,@aptCode_2,'2023-11-20'),(2,@aptCode_2,'2023-11-13'),(2,@aptCode_2,'2023-11-02'),(1,@aptCode_2,'2023-11-14'),
    (2,@aptCode_3,'2023-11-12'),(1,@aptCode_3,'2023-11-11'),(1,@aptCode_3,'2023-11-13'),(2,@aptCode_3,'2023-11-08'),(1,@aptCode_3,'2023-11-01'),
    (1,@aptCode_3,'2023-11-02'),(1,@aptCode_3,'2023-11-09'),(2,@aptCode_3,'2023-11-11'),(2,@aptCode_3,'2023-10-25'),(1,@aptCode_3,'2023-11-17'),
    (1,@aptCode_3,'2023-10-29'),(2,@aptCode_3,'2023-11-04'),(2,@aptCode_3,'2023-10-23'),(1,@aptCode_3,'2023-11-02'),(1,@aptCode_3,'2023-11-10'),
    (2,@aptCode_3,'2023-11-01'),(1,@aptCode_3,'2023-11-16'),(1,@aptCode_3,'2023-10-31'),(1,@aptCode_3,'2023-10-31'),(2,@aptCode_3,'2023-10-31'),
    (1,@aptCode_4,'2023-11-05'),(2,@aptCode_4,'2023-11-05'),(1,@aptCode_4,'2023-10-23'),(1,@aptCode_4,'2023-10-29'),(1,@aptCode_4,'2023-11-07'),
    (1,@aptCode_4,'2023-11-01'),(2,@aptCode_4,'2023-11-10'),(2,@aptCode_4,'2023-11-14'),(2,@aptCode_4,'2023-11-14'),(2,@aptCode_4,'2023-11-20'),
    (2,@aptCode_4,'2023-11-13'),(1,@aptCode_4,'2023-11-14'),(2,@aptCode_4,'2023-10-26'),(1,@aptCode_4,'2023-11-06'),(1,@aptCode_4,'2023-11-10'),
    (2,@aptCode_4,'2023-11-20'),(2,@aptCode_4,'2023-11-08'),(2,@aptCode_4,'2023-11-19'),(2,@aptCode_4,'2023-10-26'),(2,@aptCode_4,'2023-10-29'),
    (1,@aptCode_5,'2023-11-15'),(2,@aptCode_5,'2023-11-16'),(2,@aptCode_5,'2023-11-18'),(2,@aptCode_5,'2023-11-08'),(2,@aptCode_5,'2023-10-29'),
    (1,@aptCode_5,'2023-11-12'),(2,@aptCode_5,'2023-11-12'),(1,@aptCode_5,'2023-11-18'),(1,@aptCode_5,'2023-11-22'),(2,@aptCode_5,'2023-10-29'),
    (1,@aptCode_5,'2023-10-26'),(1,@aptCode_5,'2023-11-23'),(2,@aptCode_5,'2023-11-08'),(1,@aptCode_5,'2023-11-10'),(1,@aptCode_5,'2023-10-25'),
    (2,@aptCode_5,'2023-11-15'),(1,@aptCode_5,'2023-11-22'),(2,@aptCode_5,'2023-11-21'),(2,@aptCode_5,'2023-10-31'),(1,@aptCode_5,'2023-11-09');

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-19 17:06:51
