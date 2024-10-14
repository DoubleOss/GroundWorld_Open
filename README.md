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


## 👨🏻‍💻│기능 구현 목차
###   1. [동영상 재생을 위한 라이브러리 연결](https://github.com/DoubleOss/GroundWorld_Open?tab=readme-ov-file#1-%EB%A7%88%EC%9D%B8%ED%81%AC%EB%9E%98%ED%94%84%ED%8A%B8-%EB%8F%99%EC%98%81%EC%83%81-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-%EC%97%B0%EA%B2%B0)
###   2. [외부 Sound 라이브러리 연결](https://github.com/DoubleOss/GroundWorld_Open?tab=readme-ov-file#2-%EB%A7%88%EC%9D%B8%ED%81%AC%EB%9E%98%ED%94%84%ED%8A%B8-tinysound-lib-%EC%97%B0%EB%8F%99)
###   3. [아이템 등록 과정 반 자동화](https://github.com/DoubleOss/GroundWorld_Open/tree/main?tab=readme-ov-file#3-%EB%A7%88%EC%9D%B8%ED%81%AC%EB%9E%98%ED%94%84%ED%8A%B8-%EC%95%84%EC%9D%B4%ED%85%9C-%EC%B6%94%EA%B0%80%EB%A5%BC-%EC%9C%84%ED%95%9C-%EB%93%B1%EB%A1%9D%EA%B3%BC%EC%A0%95-%EA%B0%84%EC%86%8C%ED%99%94)
###   4. [HUD 시스템](https://github.com/DoubleOss/GroundWorld_Open?tab=readme-ov-file#3-hud-%EC%8B%9C%EC%8A%A4%ED%85%9C-1)
###   5. [상점 시스템](https://github.com/DoubleOss/GroundWorld_Open?tab=readme-ov-file#4-%EC%83%81%EC%A0%90-%EC%8B%9C%EC%8A%A4%ED%85%9C-1)
###   6. [스마트폰 시스템](https://github.com/DoubleOss/GroundWorld_Open?tab=readme-ov-file#5-%EC%8A%A4%EB%A7%88%ED%8A%B8%ED%8F%B0-%EC%8B%9C%EC%8A%A4%ED%85%9C-1)
###   7. [지하철 시스템](https://github.com/DoubleOss/GroundWorld_Open?tab=readme-ov-file#6-%EC%A7%80%ED%95%98%EC%B2%A0-%EC%8B%9C%EC%8A%A4%ED%85%9C-1)

***

### 1. 마인크래프트 동영상 라이브러리 연결 
> * ### Java FFmpeg Lib 마인크래프트 연결
> * ### 마인크래프트 내에서 동영상 재생 기능 추가
> * #### 활용예시
> ![2024-10-11 22;27;03](https://github.com/user-attachments/assets/8f02df60-ec38-4bb1-bf8b-69bd4c773fb9)
> ## 🔗 코드 링크
> * ### [초기 필요 파일 인게임 다운로드 설치 기능](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/proxy/ClientProxy.java#L614)
> * ### [동영상 FFmpeg 라이브러리 사용 코드](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/media/MCMediaPlayer.java)
> * ### [Gui 내 동영상 코드](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/gui/VideoGui.java#L63)

### 2. 마인크래프트 TinySound Lib 연동
> * ### 마인크래프트 자체 Sound 재생 시스템의 오류로 인한 문제 해결
> * ### 높은 Sound 반복 재생시 HashBiMap에서 중복 값 삽입 문제
> * ### Client 에서만 처리되도록 ClientSide > TinySound Init
> ## 🔗 코드 링크
> * ### [TinySound Init 코드](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/proxy/ClientProxy.java#L525)
> * ### [TinySound Lib 사용 코드](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/proxy/ClientProxy.java#L800)



### 3. 마인크래프트 아이템 추가를 위한 등록과정 간소화
> * ### 아이템 추가를 위해 lang 언어파일 + 텍스쳐 연결을 위한 json 파일 작성의 불편함을 없애기 위한 작업
> * ### init 함수 마인크래프트 실행시 자동으로 텍스쳐 체크 후 json 파일 작성
> * ### 아이템 추가를 위한 모드 제작 첫 단계에서 생성 진행 이후 함수 주석으로 차단 해야함
> ## 🔗 코드 링크
> * ### [Item Json Auto Create File](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/GroundWorld.java#L214)



### 4. HUD 시스템 
> * ### 플레이어의 실시간 스텟 [ 체력, 체온, 허기, 수분 ] 실시간 표기 
> * ### 배터리, 통화 시스템, 타이머 연동
> * ### OpenGL을 이용한 2D Texture Render
> * ### Raycast 플레이어 바라보는 오브젝트 검출 후 연출
> ![2024-10-11 21;12;30](https://github.com/user-attachments/assets/06cc838f-d1df-4141-a809-9b934674b496)  ![2024-10-11 21;26;27](https://github.com/user-attachments/assets/a7e91948-b970-460b-97bc-245932e768b4)
> * * *
> #### RayCast 연산, 유효 오브젝트시 HUD 메세지 검출 
> ![crosshair](https://github.com/user-attachments/assets/7b297c3a-a9af-48ea-9e31-0a563a89a0bf)
> * * *
> ## 🔗 코드 링크
> * ### [HUD Render 코드 링크](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/proxy/ClientProxy.java#L1553)
> * ### [Raycast 검출 코드 링크](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/proxy/ClientProxy.java#L232)
> * ### [OpenGL drawTexture 함수 링크](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/proxy/ClientProxy.java#L2245)
 



### 5. 상점 시스템
> * ### 상점 판매 아이템 수량 제한 스크롤 기능, 수량에 따른 표기 변경
> * ### 구매 판매 탭 표기 및 가격 변동
> * ### OpenGL SCISSOR 기능을 이용한 Scroll Bar 구현
> ![2024-10-11 21;14;31](https://github.com/user-attachments/assets/68676eba-4e58-4db6-9089-8e6524472dbd)
> * * *
> ## 🔗 코드 링크
> ### [상점 Gui 코드 링크](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/gui/GuiShop.java#L346)
> ### [상점 데이터 코드 링크](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/variable/Variable.java#L357)
> ### [상점 Server와 유저간 데이터 Sync 코드 링크](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/packet/SPacketShopDataItemRemove.java)
> ### [OpenGL SCISSOR Scroll Bar 코드](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/gui/GuiShop.java#L713)

* * *

### 6. 스마트폰 시스템 
> * ### 통화 시스템: 1:1 개인 통화, 안테나 + 배터리가 있어야 스마트폰 사용 가능 
> * ### 스마트폰 상점 시스템 
> * ### 긴급 공지 시스템: 일차별 공지, 실시간 운영자 공지 받는 메일함
> * ### 로봇 확인 시스템: 본인이 설치한 로봇 생존 확인용
> * ### 갤러리 시스템: 획득한 단서를 확인 가능한 갤러리 시스템

> * * *
> #### 통화 시스템 영상
> ![2024-10-11 22;07;57](https://github.com/user-attachments/assets/bdd4309e-4d5d-451a-a9f0-43f0310789dc)
> #### 긴급 공지 시스템 영상
> ![2024-10-11 22;55;26](https://github.com/user-attachments/assets/b2ed55bd-b32d-40b6-b4c4-1242b8888f7e)
> #### 갤러리 시스템
> ![2024-10-11 23;02;42](https://github.com/user-attachments/assets/d943213f-e6da-4df0-b7ed-195b95e4707a)
> ## 🔗 코드 링크
> ### [갤러리 시스템 코드](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/gui/GuiPhone.java#L1824)
> ### [긴급 공지 시스템 코드](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/gui/GuiPhone.java#L1923)



### 7. 지하철 시스템
> ### 지하철 시스템 영상
> ![Video Label](http://img.youtube.com/vi/FGVWafqkUMY/0.jpg)
>
> (https://youtu.be/FGVWafqkUMY)
> 
> ### [지하철 탑승 Gui 코드](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/gui/GuiSubway.java)
> ### [지하철 엔티티 코드](https://github.com/DoubleOss/GroundWorld_Open/blob/main/src/main/java/com/doubleos/gw/entity/EntitySubWay.java)


