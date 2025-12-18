プロジェクト計画書：次世代デジタル・レフト式手帳『Chronos Logic』

1. プロジェクト概要

現代の「多機能すぎて使いにくい」タスク管理ツールへのアンチテーゼとして、アナログ手帳の自由度とデジタルの自動生成を融合させた、意志力を最小化する週間手帳アプリケーション。

核心的価値（Concept）

Ghost Routine: 前日の未完了タスクと習慣を自動で「翌日の枠」へ再配置。

Sacrifice Logic: 物理的に不可能な予定に対し、システムが「何を諦めるか」を提案。

Buffer-Aware: 予定間の「聖域（移動・準備時間）」を可視化し、無理な計画を未然に防ぐ。

2. 技術スタック・アーキテクチャ

「技術の無駄遣い」を排除し、保守性とパフォーマンスを最優先した構成。

レイヤー

技術

役割

Backend

Java 21 / Spring Boot 3

厳密なロジック演算、SSOT（データの主権）、バッチ処理。

Frontend

React / TypeScript

滑らかな「レフト式」UI、リアルタイムの衝突シミュレーション。

Database

MySQL 8.0

予定・習慣・メタデータの永続化。

Cache/PubSub

Redis

生成完了通知（SSE用）および一時的なキャッシュ。

Infrastructure

Docker Compose

開発環境の隠蔽と、本番環境へのポータビリティ確保。

システム構成図

3. 主要ロジック設計

① 習慣生成エンジン（Ghost Routine）

実行タイミング: 各ユーザー設定の day_reset_time（プル型/バッチ併用）。

アルゴリズム: $O(N \log N)$ のスイープライン法による衝突検知。

ストライク制: 3日連続で放置されたタスクは「墓場（DEFERRED）」へ自動退避。

② メタデータ駆動による「法典（JSON）」の統治

chronos-rule.json を唯一の真実とし、Java と TypeScript で同一のバリデーションを実行。

X-Rule-Version: 通信ヘッダーによるバージョンの自動追従と再同期。

③ 犠牲の論理（Sacrifice Logic）

予定が24時間に収まらない場合、優先順位に基づいて「スキップ」を提案。

低優先度の習慣の非表示提案

停滞しているGhostタスクの強制DEFERRED化

Bufferの段階的縮小（15分 $\rightarrow$ 5分）

4. データベース設計（主要テーブル）

users

day_reset_time: ユーザーごとの「今日」が始まる時刻。

last_generated_date: 二重生成防止用のフラグ。

daily_tasks (Snapshot)

id: UUID (v7推奨：時系列ソート可能)

origin_master_name_backup: マスタ削除耐性用の名称保持。

generation_count: 持ち越し回数（3ストライク判定用）。

has_conflict: UI警告用のフラグ。

5. 開発ロードマップ

Phase 1: Core Engine & Contract (1-2週)

chronos-rule.json のスキーマ定義。

Java / TS 両環境での ConflictDetector 実装とクロス・ユニットテストの完遂。

Phase 2: Reactive UI & Sync (2-3週)

「レフト式」ドラッグ＆ドロップ画面の構築。

Page Visibility API を用いたモバイル・再同期ロジックの実装。

SSE (Server-Sent Events) による生成ステータスのリアルタイム通知。

Phase 3: Resilience & UX (3-4週)

Resilience4j によるサーキットブレーカーの導入。

「パニック・モード」および「墓場（DEFERRED）」の救済UI実装。

本番想定の負荷試験（HikariCPのチューニング）。

6. アドバイザーからの最終警告

「賢すぎる」を作るな: システムはあくまでガイドだ。最後に決定するのはユーザーである。

「Date」を信用するな: 時刻計算はすべて「0時からの経過分（Minutes from midnight）」の数値で行え。

「10万人の朝」を想像しろ: 20msの遅延が牙を剥く。インデックス一つ、コネクション一つを疎かにするな。

Status: Ready for Development