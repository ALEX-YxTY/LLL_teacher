package com.meishipintu.lll_office.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import com.meishipintu.lll_office.R

class ContractorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contractor)
        initUI()
    }

    private fun initUI() {
        findViewById(R.id.bt_back).setOnClickListener{
            onBackPressed()
        }
        findViewById(R.id.refuse).setOnClickListener{
            onBackPressed()
        }
        findViewById(R.id.agree).setOnClickListener{
            val intent = Intent(this, RegistActivity::class.java)
            intent.putExtra("from", 1)  //from=1 注册  from=2 找回密码
            startActivity(intent)
            this.finish()
        }
        val title = findViewById(R.id.tv_title) as TextView
        title.text = "注册协议"
        val textView = findViewById(R.id.contractor) as TextView
        textView.text = Html.fromHtml("<p style=\"text-align:center\">\n" +
                "    <strong><span style=\"font-family: 宋体;font-size: 21px\"><span style=\"font-family:宋体\"><br/></span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <strong><span style=\"font-family: 宋体;font-size: 21px\"><span style=\"font-family:宋体\"><br/></span></span></strong>\n" +
                "</p>\n" +
                "<p style=\"text-align: center;\">\n" +
                "    <strong><span style=\"font-family: 宋体;font-size: 24px\"><span style=\"font-family:宋体\">拉力郎用户服务协议</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <strong><span style=\"font-family: 宋体;font-size: 21px\"><span style=\"font-family:宋体\"><br/></span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">欢迎使用拉力郎共享师资</span>APP<span style=\"font-family:宋体\">！拉力郎共享师资是由南京学爱思教育咨询有限公司开发、运营，致力于打造一个精准匹配，急速到岗的师资招聘服务平台，让优秀的教师不再难以寻觅。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">请用户在访问和使用拉力郎共享师资提供的服务前仔细阅读本协议，用户点击</span>“提交注册”，即表示已阅读并同意遵守拉力郎共享师资<span style=\"font-family:Calibri\">APP</span><span style=\"font-family:宋体\">服务条款。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-weight:bold;font-size:14px\">一、</span><strong><span style=\"font-family: 宋体;font-size: 14px\"><span style=\"font-family:宋体\">用户</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">使用拉力郎共享师资服务，包括教师端和机构端。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教师用户是具有法定的相应权利能力和行为能力的自然人，能够独立承担法律责任。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">机构用户是指合法成立并存续的具有法人主体资格的企业、非企业单位入驻</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">的非法人单位。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教师用户和机构用户以下简称教师和机构，合称用户。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-weight:bold;font-size:14px\">二、</span><strong><span style=\"font-family: 宋体;font-size: 14px\"><span style=\"font-family:宋体\">拉力郎共享师资服务</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">用户应了解并知悉，拉力郎共享师资是为教育培训机构或学校提供师资招聘服务的平台，机构和教师在平台内完成独立的信息匹配。如在线下面试或工作过程中导致用户遭受相关财产及人身损害的，用户应直接向另一方追责，拉力郎共享师资免于承担因机构和教师之间服务协议履行所致任何纠纷的赔偿责任。同时，拉力郎共享师资建立了完善的客户服务体系，力争帮助或促使机构或教师妥善解决纠纷。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-weight:bold;font-size:14px\">三、</span><strong><span style=\"font-family: 宋体;font-size: 14px\"><span style=\"font-family:宋体\">使用规则</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-size:14px\">1.</span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教师入驻及信息发布</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教师</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">可自主入驻</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">将履行网络服务提供者的义务，在后台审核</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教师</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">师的身份及资质，包括但不限于</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">师的身份证、教师证、学历证书等，但是，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">无法就</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">师所有信息内容的准确性、完整性、真实性等做出实质保证。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">通过审核后，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">师入驻即行生效，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">师将在跟</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">上拥有专属的个人</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">主页</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教师</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">可自行在主页上传展示其简历、照片、文章、教学</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">经历</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">、</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">学历</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">等内容。</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">师需对其展示的所有信息的真实性、合法性、完整性及有效性承担法律责任。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-size:14px\">2.</span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">机构入驻</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">机构用户通过注册并经审核入驻</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">后，将在</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">上拥有专属的</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">机构主页</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">，机构可在</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">主页内</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">展示其</span>logo<span style=\"font-family:宋体\">、基本信息、介绍、地址路线、课程、</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">招聘信息</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">等内容；</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">通过网络推广积极提升机构的业界知名度。</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">为机构</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">主页</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">提供技术服务。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">机构应对自身提交及上传</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">的资料信息的真实性、合法性、有效性承担法律责任。不得利用</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">平台发布虚假信息、侵权信息及其他违法信息，不得利用</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资提供</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">的服务从事违法活动。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-size:14px\">3.</span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">账户管理</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">用户使用拉力郎共享师资服务，应负责维护自己账户的保密性并限制第三方使用</span>/<span style=\"font-family:宋体\">访问其计算机或移动设备，用户对其账户和密码下发生的所有活动承担法律责任。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-size:14px\">4.</span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">评价功能</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教师和机构完成线上匹配，并在线下面试后，机构可在拉力郎共享师资</span>APP<span style=\"font-family:宋体\">上对教师的面试详情做出相应的评价。机构应保证评价的真实性和客观性，对不真实不客观或侵犯教师合法权益的评价，拉力郎共享师资平台有权依法删除。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-size:14px\">5.</span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">订单及支付</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">本条款所述订单是指机构用户购买</span>app<span style=\"font-family:宋体\">内提供的职位发布次数及面试邀约功能的订单，机构在支付前请根据自身实际招聘需求购买相应套餐。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">购买相应套餐服务后，拉力郎共享师资暂不提供退款服务。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-weight:bold;font-size:14px\">四、</span><strong><span style=\"font-family: 宋体;font-size: 14px\"><span style=\"font-family:宋体\">双方的权利义务及法律责任</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">1</span><span style=\";font-family:宋体;font-size:14px\">.<span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">享有对本协议约定服务的监督、提示、检查、纠正等权利。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">2</span><span style=\";font-family:宋体;font-size:14px\">.<span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">有权保留用户注册及使用时预留的所有信息。</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">在线上线下针对</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">服务进行宣传推广活动时，有权合理使用用户预留的信息。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">3</span><span style=\";font-family:宋体;font-size:14px\">.<span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">有权删除或屏蔽用户上传的非法及侵权信息。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">4</span><span style=\";font-family:宋体;font-size:14px\">.<span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">谨慎使用用户的所有信息，非依法律规定及用户许可，不得向任何第三方透露用户信息。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">5</span><span style=\";font-family:宋体;font-size:14px\">.<span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">不对任何其他第三方搜索引擎的合法信息抓取功能采取屏蔽措施。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">6</span><span style=\";font-family:宋体;font-size:14px\">.<span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">在首页底导为任何涉嫌侵权的权利人设置便捷的投诉与举报渠道，并依法采取合理救济措施。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">7</span><span style=\";font-family:宋体;font-size:14px\">.</span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">用户须为合法目的使用</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">提供的平台服务，用户不得利用</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">平台发布任何与教育服务无关的商业信息，包括但不限于销售信息、寻求合作信息等。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">8</span><span style=\";font-family:宋体;font-size:14px\">.</span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">用户对其发布的信息的真实性、合法性、有效性及完整性承担法律责任。用户不得在</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">平台上发布任何虚假、违法信息及言论。用户上传</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">的任何内容如涉嫌侵犯第三方合法权利的，用户须独立承担因此所产生法律责任。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">9</span><span style=\";font-family:宋体;font-size:14px\">.</span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">用户仅对在</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">上享有的服务及内容享有使用权，未经</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">或其他第三方权利人的书面许可，用户不得对</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">平台</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">内的任何内容进行复制、售卖、发行等违反知识产权相关法律、法规的行为，由此所导致的一切民事、行政或刑事责任，由用户自行承担。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">10</span><span style=\";font-family:宋体;font-size:14px\">.<span style=\"font-family:宋体\">机构</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">与</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教师</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">的</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">职位匹配</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">一旦达成，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">教师和机构在线下完成面试的过程双方将</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">独立承担法律责任。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">11</span><span style=\";font-family:宋体;font-size:14px\">.<span style=\"font-family:宋体\">机构</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">须为真实</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">师资需求</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">目的使用</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">服务，不得违反诚实信用原则，采用</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">发布虚假招聘信息、恶意提高待遇</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">等方式恶意提高在</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资平台的关注度</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">12</span><span style=\";font-family:宋体;font-size:14px\">.<span style=\"font-family:宋体\">机构用户应为自己发布的招聘信息负责，对恶意发布招聘信息，以获取教师信息为目的的行为，平台有权对机构发布的职位信息进行下线处理。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">13.<span style=\"font-family:宋体\">拉力郎共享师资有权保留并合理使用机构及其教师注册及上传的所有信息。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">14.<span style=\"font-family:宋体\">拉力郎共享师资谨慎使用用户的所有信息，非依法律规定及用户许可，不得向任何第三方透露用户信息。拉力郎共享师资对相关信息采用专业加密存储与传输方式，保障用户个人信息的安全。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">15.<span style=\"font-family:宋体\">用户上传拉力郎共享师资的任何内容如涉嫌侵犯第三方合法权益的，拉力郎共享师资有权采取删除、屏蔽或断开链接等技术措施，用户须独立承担因侵权所产生法律责任</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">1</span><span style=\";font-family:宋体;font-size:14px\">6.</span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">用户不得恶意攻击、辱骂、诽谤、骚扰</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">工作人员或</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">上的其他用户。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">17.<span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">受理</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">用户</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">投诉事件</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">后</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">被投诉用户应积极协助并妥善处理。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">1</span><span style=\";font-family:宋体;font-size:14px\">8.</span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">用户违反本协议约定情节严重的，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">有权关闭用户账户。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-weight:bold;font-size:14px\">五、</span><strong><span style=\"font-family: 宋体;font-size: 14px\"><span style=\"font-family:宋体\">用户注意义务特别提示</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">用户上传</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">平台的信息不得含有以下内容：</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">（一）反对宪法确定的基本原则的；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">（二）危害国家统一、主权和领土完整的；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">（三）泄露国家秘密、危害国家安全或者损害国家荣誉和利益的；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">（四）煽动民族仇恨、民族歧视，破坏民族团结，或者侵害民族风俗、习惯的；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">（五）宣扬邪教、迷信的；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">（六）扰乱社会秩序，破坏社会稳定的；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">（七）诱导未成年人违法犯罪和渲染暴力、色情、赌博、恐怖活动的；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">（八）侮辱或者诽谤他人，侵害公民个人隐私等他人合法权益的；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">（九）危害社会公德，损害民族优秀文化传统的；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">（十）有关法律、行政法规和国家规定禁止的其他内容。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-weight:bold;font-size:14px\">六、</span><strong><span style=\"font-family: 宋体;font-size: 14px\"><span style=\"font-family:宋体\">知识产权</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">1.</span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">软件使用</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">用户需要下载</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">提供的手机</span>/pad<span style=\"font-family:宋体\">软件并安装后方得以从客户端使用</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">所提供的服务。</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">在此授予用户免费的、不可转让的、非独占的全球性个人许可，允许用户使用由</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">提供的、包含在服务中的软件。但是，用户不得复制、修改、发布、出售或出租</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">的服务软件或所含软件的任何部分，也不得进行反向工程或试图提取该软件的源代码。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">2.</span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">企业标识及商标</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">企业标识及商标均为</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">所有的合法财产。用户未经</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">书面许可不得擅自使用，不得以任何方式或理由对</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">网站标识或商标的任何部分进行使用、复制、修改、传播或与其它产品捆绑使用，不得以任何可能引起消费者混淆的方式或任何诋毁或诽谤</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">的方式用于任何商品或服务上。未经</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">的书面许可，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">网站上的任何内容都不应被解释为以默许或其他方式授予许可或使用</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">网站上出现的标识或商标的权利。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-weight:bold;font-size:14px\">七、</span><strong><span style=\"font-family: 宋体;font-size: 14px\"><span style=\"font-family:宋体\">免责条款</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">如发生以下情况，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">不对用户的直接或间接损失承担法律责任：</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">1<span style=\"font-family:宋体\">、</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">系信息服务平台，不保证该等信息的准确性、有效性、及时性或完整性。提供信息的真实性、合法性、有效性及完整性等由信息提供者承担相关法律责任；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">2<span style=\"font-family:宋体\">、不可抗力事件导致的服务中断或</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">无法控制的原因所导致的用户损失，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">不承担任何责任；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">3<span style=\"font-family:宋体\">、用户使用</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span>APP</span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">（包括链接到第三方网站或自第三方网站链接）而可能产生的计算机病毒、黑客入侵、系统失灵或功能紊乱等导致的用户损失，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">不承担任何责任；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">4<span style=\"font-family:宋体\">、由于用户将个人注册账号信息告知他人或与他人共享注册帐号，由此导致的任何风险或损失，由用户自行承担；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">5<span style=\"font-family:宋体\">、用户的</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">手机</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">软件、系统、硬件和通信线路出现故障或自身操作不当；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">6<span style=\"font-family:宋体\">、由于网络信号不稳定等原因所引起的登录失败、资料同步不完整、页面打开速度慢等；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">7<span style=\"font-family:宋体\">、用户发布的内容被他人转发、复制等传播可能带来的风险和责任；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">8<span style=\"font-family:宋体\">、</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">机构与教师</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">之间或与任何第三人间的违约行为、侵权责任等，由有关当事人自行承担法律责任；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">9<span style=\"font-family:宋体\">、其他</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">无法控制的原因；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">10<span style=\"font-family:宋体\">、如因系统维护或升级而需要暂停网络服务，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">将尽可能事先在</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span>app<span style=\"font-family:宋体\">内</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">进行通知。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-weight:bold;font-size:14px\">八、</span><strong><span style=\"font-family: 宋体;font-size: 14px\"><span style=\"font-family:宋体\">服务终止</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">用户在使用</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">服务的过程中，具有下列情形时，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">有权终止对该用户的服务：</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">1<span style=\"font-family:宋体\">、用户以非法目的使用</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">服务；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">2<span style=\"font-family:宋体\">、用户不以</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">真实招聘教师为</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">目的使用</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">服务；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">3<span style=\"font-family:宋体\">、用户存在多次被投诉等不良记录的；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">4<span style=\"font-family:宋体\">、其他侵犯</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">合法权益的行为。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <strong><span style=\"font-family: 宋体;font-size: 14px\"><span style=\"font-family:宋体\">九、</span></span></strong><strong><span style=\"font-family: Calibri;font-size: 14px\"><span style=\"font-family:宋体\">法律适用与争议解决</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">1<span style=\"font-family:宋体\">、本协议的履行与解释均适用中华人民共和国法律。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">2<span style=\"font-family:宋体\">、</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">与用户之间的任何争议应友好协商解决，协商不成的，任一方有权将争议提交</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">南京市鼓楼区</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">人民法院诉讼解决。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <strong><span style=\"font-family: Calibri;font-size: 14px\"><span style=\"font-family:宋体\">十、本协议条款的修改权与可分性</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">1<span style=\"font-family:宋体\">、为更好地提供服务并符合相关监管政策，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">有权及时修改本协议条款。请用户定期查阅本协议条款。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">2<span style=\"font-family:宋体\">、本协议条款中任何一条被视为无效或因任何理由不可执行，不影响任何其余条款的有效性和可执行性。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <strong><span style=\"font-family: Calibri;font-size: 14px\"><span style=\"font-family:宋体\">十</span></span></strong><strong><span style=\"font-family: 宋体;font-size: 14px\"><span style=\"font-family:宋体\">一</span></span></strong><strong><span style=\"font-family: Calibri;font-size: 14px\"><span style=\"font-family:宋体\">、通知</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">将通过在</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">本</span>APP<span style=\"font-family:宋体\">内</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">发布通知或其他方式与用户进行联系。用户同意用网站发布通知方式接收所有协议、通知、披露和其他信息。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family:宋体;font-weight:bold;font-size:14px\">十二、</span><strong><span style=\"font-family: 宋体;font-size: 14px\"><span style=\"font-family:宋体\">隐私声明</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">承诺将按照本隐私声明收集、使用和披露用户信息，除本声明另有规定外，不会在未经用户许可的情况下向第三方或公众披露用户信息：</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">1<span style=\"font-family:宋体\">、用户信息的范围包括：用户注册的账户信息、用户上传的信息、</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">自动接收的用户信息、</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">通过合法方式收集的用户信息等。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">2<span style=\"font-family:宋体\">、用户信息的收集、使用和披露：</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">为了给用户提供更优质的服务，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">保留收集用户</span>cookie<span style=\"font-family:宋体\">信息等权利，</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">不会向第三方披露任何可能用以识别用户个人身份的信息；</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <strong><span style=\"font-family: Calibri;font-size: 14px\"><span style=\"font-family:宋体\">十</span></span></strong><strong><span style=\"font-family: 宋体;font-size: 14px\"><span style=\"font-family:宋体\">三</span></span></strong><strong><span style=\"font-family: Calibri;font-size: 14px\"><span style=\"font-family:宋体\">、协议生效</span></span></strong>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">1<span style=\"font-family:宋体\">、本协议自用户注册</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">之日起生效。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:Calibri;font-size:14px\">2<span style=\"font-family:宋体\">、</span></span><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">拉力郎共享师资</span></span><span style=\";font-family:Calibri;font-size:14px\"><span style=\"font-family:宋体\">在法律许可范围内对本协议拥有解释权。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\";font-family:宋体;font-size:14px\">&nbsp;</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>")
    }
}
