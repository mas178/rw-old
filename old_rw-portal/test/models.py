import unittest

from portal import app
from portal.database import Base, create_all, drop_all
from portal.models import Process, Author, Category, User, delete_all


class TestProcess(unittest.TestCase):
    def setUp(self):
        delete_all()
        drop_all()
        create_all()

        authors_test_data = [Author('Rational Works'), Author('Medley'), Author('株式会社DCT'), Author('アクセンチュア株式会社'), Author('株式会社橋本屋')]
        categories_test_data = [Category('中途採用'), Category('人事'), Category('ベンチャー'), Category('名刺'), Category('各種申請'),
                                Category('総務'), Category('個人情報'), Category('マイナンバー'), Category('セキュリティ'), Category('クレーム管理')]
        processes_test_data = [
            Process(
                name='中途採用',
                title='シンプルな中途採用プロセス',
                description='メールでは管理しきれないけど、大げさなシステムを導入するほどではないといった場合に最適です。',
                image_url='http://yahoo.co.jp',
                authors=[authors_test_data[0]],
                categories=[categories_test_data[0], categories_test_data[1], categories_test_data[2]]),
            Process(
                name='中途採用',
                title='中途採用業務 (Medley)',
                description='数多くのベンチャー企業が採用している中途採用プロセスです。面接数を柔軟に変更できます。',
                image_url='http://yahoo.co.jp',
                authors=[authors_test_data[1]],
                categories=[categories_test_data[0], categories_test_data[1], categories_test_data[2]]),
            Process(
                name='名刺発行申請',
                title='名刺発行申請 (Rational Works)',
                description='シンプルな名刺発行申請プロセスです。Rational Worksに要望を出すことによって、御社独自のプロセスにカスタマイズすることが出来ます。',
                image_url='http://yahoo.co.jp',
                authors=[authors_test_data[0]],
                categories=[categories_test_data[3], categories_test_data[4], categories_test_data[5]]),
            Process(
                name='個人情報管理',
                title='個人情報管理【マイナンバー】 (Rational Works)',
                description='マイナンバーの管理を含む、個人情報管理プロセスです。',
                image_url='http://yahoo.co.jp',
                authors=[authors_test_data[0]],
                categories=[categories_test_data[6], categories_test_data[7], categories_test_data[8]]),
            Process(
                name='提案管理',
                title='社内システムに関する要望/提案管理 (Rational Works)',
                description='社内システムに関する要望や改善提案を行う手順は明確ですか？せっかく社員が改善案を思いついても、それを上げるプロセスが明確でないと、大量の要望が上げられて収集がつかなくなったり、逆に、内に...',
                image_url='http://yahoo.co.jp',
                authors=[authors_test_data[0]],
                categories=[categories_test_data[9]]),
            Process(
                name='提案管理',
                title='社内システムに関する要望/提案管理 (Rational Works)',
                description='社内システムに関する要望や改善提案を行う手順は明確ですか？せっかく社員が改善案を思いついても、それを上げるプロセスが明確でないと、大量の要望が上げられて収集がつかなくなったり、逆に、内に...',
                image_url='http://yahoo.co.jp',
                authors=[authors_test_data[0]],
                categories=[categories_test_data[9]]),
            Process(
                name='提案管理',
                title='社内システムに関する要望/提案管理 (Rational Works)',
                description='社内システムに関する要望や改善提案を行う手順は明確ですか？せっかく社員が改善案を思いついても、それを上げるプロセスが明確でないと、大量の要望が上げられて収集がつかなくなったり、逆に、内に...',
                image_url='http://yahoo.co.jp',
                authors=[authors_test_data[0]],
                categories=[categories_test_data[9]]),
            Process(
                name='提案管理',
                title='社内システムに関する要望/提案管理 (Rational Works)',
                description='社内システムに関する要望や改善提案を行う手順は明確ですか？せっかく社員が改善案を思いついても、それを上げるプロセスが明確でないと、大量の要望が上げられて収集がつかなくなったり、逆に、内に...',
                image_url='http://yahoo.co.jp',
                authors=[authors_test_data[0]],
                categories=[categories_test_data[9]]),
        ]

        # Setup test data
        for author in authors_test_data:
            Base.session.add(author)

        for category in categories_test_data:
            Base.session.add(category)

        for process in processes_test_data:
            Base.session.add(process)

        Base.session.commit()

    def tearDown(self):
        pass

    def test_find(self):
        app.logger.debug(len(Process.find()))
        assert len(Process.find()) is 8
        assert len(Process.find(category=1)) is 2
        assert len(Process.find(free_word='社内システム')) is 4  # 本当は4になるべき

    def test_user(self):
        assert len(User.query.all()) is 0
        user = User()
        Base.session.add(user)
        Base.session.commit()
        assert len(User.query.all()) is 1

    if __name__ == '__main__':
        unittest.main()
