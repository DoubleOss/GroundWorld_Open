# 양띵 TV 지상세계 프로젝트 모드

## 📝│개요
> ### 근미래 (2070~2100년), SF , 복제인간
> 
> ### 생존 협동 스토리 콘텐츠
> ### 제작기간 2023.11 ~ 2024.06
> ### 사용기술: Java, JDA(Java Discord Api), Forge Api, FFmpeg Lib, TinySound Lib, Gecko Lib

## 💬│시스템 간략 소개 영상
> ![Video Label](http://img.youtube.com/vi/4PT_Dvr42-Y/0.jpg)
> 
> (https://youtu.be/4PT_Dvr42-Y)


## 👨🏻‍💻│기능 구현
###   1. 동영상 재생을 위한 라이브러리 연결
###   2. 외부 Sound 라이브러리 연결
###   3. HUD 시스템
###   4. 상점 시스템
###   5. 스마트폰 시스템
###   6. 지하철 시스템

***

### 1. 마인크래프트 동영상 라이브러리 연결 
> * ### Java FFmpeg Lib 마인크래프트 연결
> * ### 마인크래프트 내에서 동영상 재생 기능 추가
> * #### 활용예시
> ![2024-10-11 22;27;03](https://github.com/user-attachments/assets/8f02df60-ec38-4bb1-bf8b-69bd4c773fb9)

> ### 동영상 FFmpeg 라이브러리 사용 코드
> ### Gui 동영상 코드

### 2. 마인크래프트 TinySound Lib 연동
> * ### 마인크래프트 자체 Sound 재생 시스템의 오류로 인한 문제 해결
> * ### 높은 Sound 반복 재생시 HashBiMap에서 중복 값 삽입 문제
> * ### Client 에서만 처리되도록 ClientSide > TinySound Init
> * 
> ### TinySound Init 코드
> ### TinySound Lib 사용 코드


### 3. HUD 시스템 
> * ### 플레이어의 실시간 스텟 [ 체력, 체온, 허기, 수분 ] 실시간 표기 
> * ### 배터리, 통화 시스템, 타이머 연동
> ![2024-10-11 21;12;30](https://github.com/user-attachments/assets/06cc838f-d1df-4141-a809-9b934674b496)  ![2024-10-11 21;26;27](https://github.com/user-attachments/assets/a7e91948-b970-460b-97bc-245932e768b4)
> ### HUD Render 코드 링크



### 4. 상점 시스템
> * ### 상점 판매 아이템 수량 제한 스크롤 기능, 수량에 따른 표기 변경
> * ### 구매 판매 탭 표기 및 가격 변동
> ![2024-10-11 21;14;31](https://github.com/user-attachments/assets/68676eba-4e58-4db6-9089-8e6524472dbd)
> ### 상점 Gui 코드 링크
> ### 상점 데이터 코드 링크 
> ### 상점 Server와 유저간 데이터 Sync 코드 링크

* * *

### 5. 스마트폰 시스템 
> * ### 통화 시스템: 1:1 개인 통화, 안테나 + 배터리가 있어야 스마트폰 사용 가능 
> * ### 스마트폰 상점 시스템 
> * ### 긴급 공지 시스템: 일차별 공지, 실시간 운영자 공지 받는 메일함
> * ### 로봇 확인 시스템: 본인이 설치한 로봇 생존 확인용
> * ### 갤러리 시스템: 획득한 단서를 확인 가능한 갤러리 시스템

> * * *
> #### 통화 시스템 영상
> ![2024-10-11 22;07;57](https://github.com/user-attachments/assets/bdd4309e-4d5d-451a-a9f0-43f0310789dc)
> ### 통화 시스템 코드
> * * *
> #### 긴급 공지 시스템 영상
> ![2024-10-11 22;55;26](https://github.com/user-attachments/assets/b2ed55bd-b32d-40b6-b4c4-1242b8888f7e)
> ### 긴급 공지 시스템 코드
> * * *
> #### 갤러리 시스템
> ![2024-10-11 23;02;42](https://github.com/user-attachments/assets/d943213f-e6da-4df0-b7ed-195b95e4707a)
> ### 갤러리 시스템 코드



### 6. 지하철 시스템
> ### 지하철 시스템 영상
> ![Video Label](http://img.youtube.com/vi/FGVWafqkUMY/0.jpg)
>
> (https://youtu.be/FGVWafqkUMY)
> 
> ### 지하철 탑승 Gui 코드
> ### 지하철 엔티티 소환 코드


