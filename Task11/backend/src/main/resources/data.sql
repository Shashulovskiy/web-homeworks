CREATE TABLE `post` (
                        `id` bigint(20) NOT NULL,
                        `creation_time` datetime DEFAULT NULL,
                        `text` longtext NOT NULL,
                        `title` varchar(60) NOT NULL,
                        `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `post` (`id`, `creation_time`, `text`, `title`, `user_id`) VALUES
(1, '2019-12-16 17:59:44', 'Âñåì ïðèâåò!\r\n\r\nÑêîðî ñîñòîèòñÿ Codeforces Global Round 6, âðåìÿ íà÷àëà: âòîðíèê, 17 äåêàáðÿ 2019 ã. â 18:05.\r\n\r\nÝòî øåñòîé ðàóíä èç ñåðèè Codeforces Global Rounds, êîòîðàÿ ïðîâîäèòñÿ ïðè ïîääåðæêå XTX Markets. Â ðàóíäàõ ìîãóò ó÷àñòâîâàòü âñå, ðåéòèíã òîæå áóäåò ïåðåñ÷èòàí äëÿ âñåõ.\r\n\r\nÑîðåâíîâàíèå ïðîäëèòñÿ 2 ÷àñà 30 ìèíóò, âàñ îæèäàåò 8 çàäà÷, ïðè ýòîì îäíà èç çàäà÷ áóäåò ïðåäñòàâëåíà â äâóõ âåðñèÿõ. Ðàçáàëëîâêà áóäåò îáúÿâëåíà íåçàäîëãî äî ñòàðòà ðàóíäà.', 'Codeforces Global Round 6', 1),
(2, '2019-12-16 18:00:50', 'Â ýòîì ñåìåñòðå çàïèñàë íà âèäåî âñå ëåêöèè êóðñà \"Àëãîðèòìû è ñòðóêòóðû äàííûõ\", êîòîðûé ÿ ÷èòàþ â ÈÒÌÎ. Ëåêöèè ñòðèìèëèñü â ïðÿìîì ýôèðå íà òâè÷ è ïîòîì âûêëàäûâàëèñü íà þòóá.\r\n\r\nÊóðñ ñêîðåå àêàäåìè÷åñêèé, à íå îëèìïèàäíûé, íî äóìàþ ìíîãèì íà÷èíàþùèì (è íå òîëüêî) îëèìïèàäíèêàì òîæå áóäåò èíòåðåñíî. Íàïðèìåð, ýòè ëåêöèè.', 'Âèäåîëåêöèè ìîåãî êóðñà â ÈÒÌÎ', 2),
(3, '2019-12-16 18:00:39', 'Ïðèâåò! Â ÷åòâåðã, 12 äåêàáðÿ 2019 ã. â 16:35 íà÷í¸òñÿ Codeforces Round #605 (Div. 3)  î÷åðåäíîé Codeforces ðàóíä äëÿ òðåòüåãî äèâèçèîíà. Â ýòîì ðàóíäå áóäåò 6 èëè 7 çàäà÷ (èëè 8), êîòîðûå ïîäîáðàíû ïî ñëîæíîñòè òàê, ÷òîáû ñîñòàâèòü èíòåðåñíîå ñîðåâíîâàíèå äëÿ ó÷àñòíèêîâ ñ ðåéòèíãàìè äî 1600. Îäíàêî âñå æåëàþùèå, ÷åé ðåéòèíã 1600 è âûøå ìîãóò çàðåãèñòðèðîâàòüñÿ íà ðàóíä âíå êîíêóðñà.\r\n\r\nÐàóíä ïðîéäåò ïî ïðàâèëàì îáðàçîâàòåëüíûõ ðàóíäîâ. Òàêèì îáðàçîì, âî âðåìÿ ðàóíäà çàäà÷è áóäóò òåñòèðîâàòüñÿ íà ïðåäâàðèòåëüíûõ òåñòàõ, à ïîñëå ðàóíäà áóäåò 12-òè ÷àñîâàÿ ôàçà îòêðûòûõ âçëîìîâ. ß ïîñòàðàëñÿ ñäåëàòü ïðèëè÷íûå òåñòû  òàê æå êàê è âû áóäó ðàññòðîåí, åñëè ó ìíîãèõ ïîïàäàþò ðåøåíèÿ ïîñëå îêîí÷àíèÿ êîíòåñòà.\r\n\r\nÂàì áóäåò ïðåäëîæåíî 6 èëè 7 (èëè 8) çàäà÷ è 2 ÷àñà íà èõ ðåøåíèå.\r\n\r\nØòðàô çà íåâåðíóþ ïîïûòêó â ýòîì ðàóíäå (è ïîñëåäóþùèõ Div. 3 ðàóíäàõ) áóäåò ðàâíÿòüñÿ 10 ìèíóòàì.', 'Codeforces Round #605 (Div. 3)', 1);

CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL,
                        `admin` bit(1) NOT NULL,
                        `creation_time` datetime DEFAULT NULL,
                        `login` varchar(24) NOT NULL,
                        `name` varchar(100) NOT NULL,
                        `passwordSha` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` (`id`, `admin`, `creation_time`, `login`, `name`, `passwordSha`) VALUES
(1, b'0', '2019-12-16 17:57:45', 'mike', 'Mike Mirzayanov', '783a78e11175b9d9699599b39c475ab77950ee31'),
(2, b'0', '2019-12-16 17:58:40', 'tester', 'Tester Testerov', '4c6f0278e40dbd369cf6406b29123b18cc3df578');

ALTER TABLE `post`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`);

ALTER TABLE `user`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `UKew1hvam8uwaknuaellwhqchhb` (`login`);

ALTER TABLE `post`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `user`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `post`
    ADD CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
