import { ProLayoutProps } from '@ant-design/pro-components';

/**
 * @name
 */
const defaultSettings: ProLayoutProps & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'light',
  // 拂晓蓝
  colorPrimary: '#1890ff',
  layout: 'mix',
  contentWidth: 'Fluid',
  fixedHeader: false,
  fixSiderbar: true,
  splitMenus: false,
  siderMenuType: 'sub',
  colorWeak: false,
  title: 'SgAPI开放平台',
  pwa: true,
  logo: 'https://gigot-1315824716.cos.ap-chongqing.myqcloud.com/pictrue/logo48x48.png',
  iconfontUrl: '',
  token: {
    // 参见ts声明，demo 见文档，通过token 修改样式
    //https://procomponents.ant.design/components/layout#%E9%80%9A%E8%BF%87-token-%E4%BF%AE%E6%94%B9%E6%A0%B7%E5%BC%8F
  },
};

export default defaultSettings;
