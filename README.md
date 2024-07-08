![title](img/title.png "top")

# 使用技術一覧

![Static Badge](https://img.shields.io/badge/Spring_Boot-3.2.3-%236DB33F?style=plastic&logo=Spring&logoColor=%236DB33F)
![Static Badge](https://img.shields.io/badge/Docker-%232496ED?style=plastic&logo=Docker&logoColor=white)
![Static Badge](https://img.shields.io/badge/MySQL-%234479A1?style=plastic&logo=MySQL&logoColor=white)
![Static Badge](https://img.shields.io/badge/Language-Java-red?style=plastic)
![Static Badge](https://img.shields.io/badge/Git_Hub-%23181717?style=plastic&logo=GitHub&logoColor=white)

AWSについては学習中になりますので実装次第更新します。

***

# サービス概要

* 名前と誕生日をセットにidで紐づけして登録します。登録の際idは自動で採番されます。
* ユーザーを全件JSON形式で取得します。また、名前に入力した文字が含まれる人の検索やidを指定して検索する条件取得ができます。
* idを指定しそのユーザーの名前と誕生日を更新できます。
* idを指定しそのユーザーを削除することができます。

<br>
<br>
<br>

# 作成にあたって

色々なAPIを考え挑戦しようと思いましたが、__基本の「き」__ ができてこそ！  
と思い授業に近い形で作成しました。  
このAPIを他の生徒さん達が見て参考になれば！という思いで作成しました。

<br>
<br>

# セットアップについて

### 必要環境

<br>

* Java 17
* MySQL
* Docker 3.8

<br>

# ローカルでのアプリケーション起動手順

1.プロジェクトをクローンします。

```text
 git clone https://github.com/u-1pena/assignment10.git
```

2.Dockerを起動

```text
docker compose up -d
```

3.SpringBootを起動

```text
./gradlew bootRun
```

ブラウザにてcurlコマンド等でリクエスト

[localhost:8080](http://localhost:8080/users)

# アプリケーション概略図

![aplication map](img/applicationMap.png "aplicationM")

# API一覧

|   API    | Method |     path      |
|:--------:|:------:|:-------------:|
| ユーザー全件検索 |  GET   |    /users     |
| ユーザーid検索 |  GET   |  /users/{id}  |
| ユーザー名前検索 |  GET   | /users/{name} |
| ユーザー新規登録 |  POST  |    /users     |
| ユーザー更新処理 | PATCH  |  /users/{id}  |
| ユーザー削除処理 | DELETE |  /users/{id}  |

<br>

# DBについて ![Static Badge](https://img.shields.io/badge/MySQL-%234479A1?style=plastic&logo=MySQL&logoColor=white)

### テーブル名：users

|  column  |  data type   | NotNull  |   　  key   |    description     |
|:--------:|:------------:|:--------:|:----------:|:------------------:|
|    id    |     int      | NOT NULL | primaryKey |     id 自動採番　　      |
|   name   | VARCHER(100) | NOT NULL |            |         名前         |
| birthday |     DATE     | NOT NULL |            | 　誕生日（YYYY-MM-DD）表示 |

<br>
<br>

# Dockerについて![Static Badge](https://img.shields.io/badge/Docker-%232496ED?style=plastic&logo=Docker&logoColor=white)

|                     |                       |
|:--------------------|----------------------:|
| version             |                   3.8 |  
| container name      | docker-mysql-hands-on | 
| platform            |          linux/x86_64 | 
| environment         |
| MYSQL_ROOT_PASSWORD |              password |
| MYSQL_DATABASE      |             user_list | 
| MYSQL_USER          |                  user | 
| MYSQL_PASSWORD      |              password |  
| port                |             3307:3306 |

# 仕様書とシーケンス図

__[SwaggerによるAPI仕様書](https://u-1pena.github.io/assignment10/)__

<br>

### Swaggerサンプル

<br>

![swaggerSample](img/swagger.gif "swaggerSample")

# シーケンス図

```mermaid
sequenceDiagram
    Client ->> WebAPI: ユーザーを全件取得
    WebAPI ->> DB: DBにアクセス
    DB -->> WebAPI: ユーザー情報をリストで取得
    WebAPI -->> Client: 200 OK ユーザー情報をリストで取得
    Client ->> WebAPI: ユーザー情報を指定検索
    WebAPI ->> DB: DBにアクセス
    DB -->> WebAPI: 指定検索をもとにユーザー情報を取得
    WebAPI -->> Client: 200 OK 指定されたユーザーを返す
    break
        DB -->> WebAPI: 指定したユーザーが存在しない
    end
    break
        WebAPI -->> Client: 404 NotFoundを返す
    end

    Client ->> WebAPI: ユーザー情報を新規登録
    WebAPI ->> DB: ユーザーを登録
    break
        WebAPI -->> Client: 400 Bad Request Validationエラー
        participant Client
        Note right of Client: Validation error<br/>name: NotBlank<br/>birthday:YYYY/MM/DD format
    end
    DB -->> WebAPI: IDを自動採番して登録
    WebAPI -->> Client: 201 create 登録されたことを返す
    DB -->> WebAPI: 指定したユーザーが存在しない
    WebAPI -->> Client: 404 NotFoundを返す
    Client ->> WebAPI: 削除したいユーザーidを送信
    WebAPI ->> DB: DBにアクセス
    DB -->> WebAPI: 指定したidのユーザーを削除
    WebAPI -->> Client: 200 OK 削除されたことを返す
    break
        DB -->> WebAPI: 指定したユーザーが存在しない
    end
    break
        WebAPI -->> Client: 404 NotFoundを返す
    end

    Client ->> WebAPI: 更新したいユーザー情報を送信
    break
        WebAPI -->> Client: 400 Bad Request Validationエラー
        participant Client
        Note right of Client: Validation error<br/>name: NotBlank<br/>birthday:YYYY/MM/DD format
    end
    WebAPI ->> DB: DBにアクセス
    DB -->> WebAPI: 指定したidのユーザーを更新
    WebAPI -->> Client: 200 OK 更新されたことを返す
    break
        DB -->> WebAPI: 指定したユーザーが存在しない
    end
    break
        WebAPI -->> Client: 404 NotFoundを返す
    end

```

# 自動テスト

以下の自動テストを実装

* UserServiceの単体テスト
* UserMapperのDBテスト
* UserIntegrationテストの実装（結合テスト）

<br>

### これらをGithubActionsによりCI実装

* Service単体テストレポート

<br>

![UserServiceTest](img/UserServiceTest.png "Test")

<br>

* DBテストレポート

<br>

![UserMapperTest](img/UserMapperTest.png "Test")

* 結合テストレポート

<br>

![UserIntegrationTest](img/UserIntegrationTest.png "Test")

# portfolios1
