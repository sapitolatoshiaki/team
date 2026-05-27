SET foreign_key_checks=1;

USE teamdb;

INSERT INTO mst_user
(user_name, password, family_name, first_name, family_name_kana, first_name_kana, gender)
VALUES ('taro@gmail.com', '111111', '山田', '太郎', 'やまだ', 'たろう', 0);

INSERT INTO mst_category (category_name,category_description) VALUES
('ロック', 'ロック音楽のカテゴリーです'),
('ボカロ', 'ボーカロイド音楽のカテゴリーです'),
('クラシック', 'クラシック音楽のカテゴリーです');

INSERT INTO mst_product(product_name,product_name_kana,product_description,category_id,price,image_full_path,release_date,release_company) VALUES 
('Riot of Love','らいおっとおぶらぶ',
	'1999年結成、ライブハウスから生まれた音は、やがて時代そのものを飲み込み、平成の時代を駆け抜けた。そして2017年。彼らは最後のツアーを終え、静かに解散する。本作は、そのラストライブを収録した最後のライブアルバム。人生に戦いを挑む者たちへ向けた最後の歌。',
	1,4980,'/img/rock_riot_of_love.jpg','2018/4/1','4eachMusic'),
('Fall to Rise','ふぉーるとぅらいず',
	'傷つき、迷い、それでも前へ進もうとする者たちへ。『Fall to Rise』タイトルに込められた”落ちても、再び立ち上がる”という思いを軸に、聴く人それぞれの感情や経験に寄り添う作品となっている。暗闇の先にある希望を信じる、すべての人へ贈る一枚。',
	1,5500,'/img/rock_falltorise.jpg','2026/1/1','紅薔薇音楽社'),
('Midnight Reverier','みっどないとれぶりー',
	'電子の声が描く、あなた自身の記憶の旅。甘さと不安定さが共存するチルポップや記憶の狭間を彷徨うようなダークアンビエントな曲で構成される三つの断章で、真夜中のまどろみへと誘われるような一枚。',
	2,5250,'/img/vocaloid_midnight.jpg','2022/4/4','Mellow Systems'),
('初音ミクベストアルバム', 'はつねみくべすとあるばむ',
	'バーチャルシンガー・初音ミクの代表曲を集めたベストアルバム。疾走感あふれるロックから、切なく透明感のあるバラードまで、ボーカロイド文化を彩ってきた名曲たちを収録。電子の歌声が紡ぐ、未来と青春の記憶を閉じ込めた一枚。',
	2, 5000, '/img/vocaloid_miku_best.jpg','2025/1/13','Future Sound Records'),
('木星','もくせい',
	'イギリスの作曲家グスタフ・ホルストの代表曲である組曲＜惑星＞の中の一曲。1914年から作曲開始。ホルスト自身が占星術に傾倒しており、その神秘性からインスピレーションを得ている。しかし、ホルスト曰く、占星術など気にせず、自由に聞いて欲しいとのこと。',
	3,4000,'/img/classic_jupiter.jpg','2010/4/1','グスターブ交響空想楽団'),
('ベートーヴェン交響曲第7番 イ長調 作品92','べーとーう゛ぇんこうきょうきょくだいななばんいちょうちょうさくひんきゅうじゅうに',
	'ベートーヴェンの中でも特に人気の高い「交響曲第7番」。明るくてリズム感のある曲が多く、クラシックが初めての人でも聴きやすい一枚。ロイヤル・フィルの豊かな音と、ハーディングのすっきりとした指揮が魅力となっている。',
	3,5600,'/img/classic_beethoven.jpg','2020/11/23','銀座交響音楽社');