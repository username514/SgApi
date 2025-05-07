import Footer from '@/components/Footer';
import {
  loginSendVerificationCodeUsingPost,
  userLoginByEmailUsingPost,
  userLoginUsingPost,
} from '@/services/gigotapi-backend/userController';
import { LockOutlined, MobileOutlined, UserOutlined } from '@ant-design/icons';
import { LoginForm, ProFormCaptcha, ProFormText } from '@ant-design/pro-components';
import { useEmotionCss } from '@ant-design/use-emotion-css';
import {
  Helmet,
  history,
  Link,
  RequestConfig,
  useModel,
} from '@umijs/max';
import { Alert, Image, message, Modal, QRCode, Tabs, theme, Tooltip } from 'antd';
import React, { useEffect, useState } from 'react';
import Settings from '../../../../config/defaultSettings';
import WxXcxApp from './CustomIcon';
import {requestConfig} from "@/requestConfig";

const ActionIcons = () => {
  const langClassName = useEmotionCss(({ token }) => {
    return {
      marginLeft: '8px',
      color: 'rgba(0, 0, 0, 0.2)',
      fontSize: '24px',
      verticalAlign: 'middle',
      cursor: 'pointer',
      transition: 'color 0.3s',
      '&:hover': {
        color: token.colorPrimaryActive,
      },
    };
  });
  return (
    <>
    </>
  );
};
const LoginMessage: React.FC<{
  content: string;
}> = ({ content }) => {
  return (
    <Alert
      style={{
        marginBottom: 24,
      }}
      message={content}
      type="error"
      showIcon
    />
  );
};
const Login: React.FC = () => {
  const [userLoginState] = useState<API.LoginResult>({});
  const [type, setType] = useState<string>('email');
  const [loading, setLoading] = useState<boolean>(false);
  const [xcxQRVisible, setxcxQRVisible] = useState<boolean>(false);
  const { token } = theme.useToken();
  const [userAgreementVisible, setuserAgreementVisible] = useState<boolean>(false);
  const [tooltipVisible, setTooltipVisible] = useState<boolean>(true);
  const { setInitialState } = useModel('@@initialState');
  const containerClassName = useEmotionCss(() => {
    return {
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      overflow: 'auto',
      backgroundImage: 'url(/assets/tencentCloudBackGround.jpg)',
      backgroundSize: '100% 100%',
    };
  });

  setTimeout(() => {
    setTooltipVisible(false);
  }, 4000);


  const handleSubmit = async (values: any) => {
    try {
      // 登录
      let res = undefined;
      if (type === 'account') {
        res = await userLoginUsingPost({
          ...values,
        });
      }
      if (type === 'email') {
        res = await userLoginByEmailUsingPost({
          ...values,
        });
      }

      // setInitialState({loginUser: res.data, settings: Settings});
      if (res.data) {
        const urlParams = new URL(window.location.href).searchParams;
        // 解决需要点两次登录的问题
        // 这个问题是由于 React 组件更新的异步性质引起的。
        // 在调用 setInitialState 后，状态可能并没有立即更新，而你又立即执行了 history.push
        // 可以等待后增加一个定时器解决这个问题
        sessionStorage.setItem('token', res.data?.id);
        await setInitialState({
          loginUser: res.data,
        });
        setTimeout(() => {
          history.push(urlParams.get('redirect') || '/');
        }, 100);

        return;
      }
    } catch (error) {
      console.log(error);
    }
  };
  const { status, type: loginType } = userLoginState;
  return (
    <div className={containerClassName}>
      <Helmet>
        <title>
          {'登录'}- {Settings.title}
        </title>
      </Helmet>
      <div
        style={{
          flex: '1',
          padding: '32px 0',
        }}
      >
        <LoginForm
          contentStyle={{
            minWidth: 280,
            maxWidth: '75vw',
          }}
          containerStyle={{}}
          logo={<img alt="logo" src="/icons/icon-128x128.png" />}
          title="SgApi开放平台"
          subTitle={'简单便捷，助力您的开发之旅'}
          initialValues={{
            autoLogin: false,
          }}
          actions={[
            <>
            </>,
          ]}
          onFinish={async (values) => {
            await handleSubmit(values);
          }}
        >
          <Tabs
            activeKey={type}
            onChange={setType}
            centered
            items={[
              {
                key: 'email',
                label: '邮箱免注册登录',
              },
              {
                key: 'account',
                label: '账户密码登录',
              },
            ]}
          />

          {status === 'error' && loginType === 'account' && (
            <LoginMessage content={'错误的用户名和密码'} />
          )}
          {type === 'account' && (
            <>
              <ProFormText
                name="userAccount"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined />,
                }}
                placeholder={'请输入用户名'}
                rules={[
                  {
                    required: true,
                    message: '用户名是必填项！',
                  },
                ]}
              />
              <ProFormText.Password
                name="userPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined />,
                }}
                placeholder={'请输入密码'}
                rules={[
                  {
                    required: true,
                    message: '密码是必填项！',
                  },
                ]}
              />
            </>
          )}
          {status === 'error' && loginType === 'account' && (
            <LoginMessage content={'错误的用户名和密码'} />
          )}
          {/*邮箱验证码登录*/}
          {status === 'error' && loginType === 'email' && <LoginMessage content="验证码错误" />}
          {type === 'email' && (
            <>
              <ProFormText
                fieldProps={{
                  size: 'large',
                  prefix: <MobileOutlined />,
                }}
                name="email"
                placeholder={'请输入邮箱号！'}
                rules={[
                  {
                    required: true,
                    message: '邮箱号是必填项！',
                  },
                  {
                    pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                    message: '不合法的邮箱号！',
                  },
                ]}
              />
              <ProFormCaptcha
                phoneName={'email'}
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined />,
                }}
                captchaProps={{
                  size: 'large',
                }}
                placeholder={'请输入验证码！'}
                captchaTextRender={(timing, count) => {
                  if (timing) {
                    return `${count} ${'秒后重新获取'}`;
                  }
                  return '获取验证码';
                }}
                name="verificationCode"
                rules={[
                  {
                    required: true,
                    message: '验证码是必填项！',
                  },
                ]}
                onGetCaptcha={async (email) => {
                  // todo 获取验证码
                  const res = await loginSendVerificationCodeUsingPost({
                    email: email,
                    verificationCode: null,
                  });
                  if (res && res.code === 0) {
                    await setInitialState({
                      loginUser: res.data,
                    });
                  }
                  return;
                }}
              />
            </>
          )}
          <div
            style={{
              marginBottom: 24,
            }}
          >
            {/*<ProFormCheckbox noStyle name="autoLogin">*/}
            {/*  自动登录*/}
            {/*</ProFormCheckbox>*/}
            {type === 'account' && (
              <Link to="/user/register" style={{ float: 'right' }}>
                <Tooltip title="来注册一个用户吧~">新用户注册</Tooltip>
              </Link>
            )}
          </div>
        </LoginForm>
      </div>
      <Footer />
    </div>
  );
};
export default Login;
