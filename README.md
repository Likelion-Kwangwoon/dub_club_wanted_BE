---


# dub_club_wanted_BE

## dub
동아리 홍보와, 동아리 지원을 할수있는 플랫폼


---

# 핵심기능

#### 1. 회원 가입

```일반 회원과 동아리 회원으로 나누어 가입이 가능합니다.```

#### 2. 동아리 등록

```동아리 회원은 동아리의 프로필 이미지, 태그, 소개글 등을 등록할 수 있습니다.```

#### 3. 동아리 공고 및 지원서 양식 등록

```동아리 회원은 동아리 내에서 공고를 올릴 수 있으며, 지원자들에게 지원서 양식을 제공할 수 있습니다.```

#### 4. 태그별 동아리 조회

```사용자는 태그별로 동아리를 검색하고 조회할 수 있습니다.```

#### 5. 동아리 지원서 다운로드 및 제출

```일반 회원은 동아리의 지원서 양식을 다운로드하여 작성한 후, 해당 동아리에 제출할 수 있습니다.```

#### 6. 동아리 회원별 지원자 조회

```동아리 회원은 자신의 동아리에 지원한 회원들을 조회할 수 있습니다.```

#### 7. 합격/불합격 통보 보내기

```동아리 회원은 지원자들에게 합격 또는 불합격 통보를 보낼 수 있습니다.```

---

# API 엔드포인트 목록 및 사용법

https://woozy-cuticle-bfb.notion.site/dub_-wanted-5f89e6bcf87142eca927893ff04703f6?pvs=4


---

# CI/CD Flow

1. main branch 에 Push 또는 Merge
2. Github 에 작성해둔 workflow file 로 Github Actions 수행
3. build, docker image build, docker image push 수행
4. EC2 인스턴스에서 docker image pull 후, run

---

# ERD

![db설계 최종](https://github.com/s2hoon/dub_club_wanted_BE/assets/82464990/532c6e73-719f-4645-a798-1fe47da878c3)

---

# Project Structure

---

# 성능개선




