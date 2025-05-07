import { getIntroduceRowUsingGet } from '@/services/gigotapi-backend/analyseController';
import { Area, Column } from '@ant-design/plots';
import { Col, Progress, Row } from 'antd';
import numeral from 'numeral';
import { useEffect, useState } from 'react';
import type { DataItem } from '../data.d';
import useStyles from '../style.style';
import { ChartCard, Field } from './Charts';
import Trend from './Trend';

const topColResponsiveProps = {
  xs: 12,
  sm: 12,
  md: 12,
  lg: 12,
  xl: 12,
  style: {
    marginBottom: 24,
  },
};
const IntroduceRow = ({ loading, visitData }: { loading: boolean; visitData: DataItem[] }) => {
  const [introduceRow, setIntroduceRow] = useState<API.IntroduceRowVO>();

  const onload = async () => {
    // 发起请求
    const resIntroduceRow = await getIntroduceRowUsingGet();
    if (resIntroduceRow && resIntroduceRow.code === 0) {
      setIntroduceRow(resIntroduceRow.data);
    }
  };
  useEffect(() => {
    onload();
  }, []);
  return (
    <Row gutter={24}>
      <Col {...topColResponsiveProps}>
        <ChartCard
          bordered={false}
          loading={loading}
          title="近期接口平均耗时"
          total={introduceRow?.cost + 'ms'}
          footer={
            <Field
              label="调用次数"
              value={numeral(introduceRow?.interfaceInfoCount).format('0,0')}
            />          }
          contentHeight={46}
        >
          <Area
            xField="x"
            yField="y"
            shapeField="smooth"
            height={46}
            axis={false}
            style={{
              fill: 'linear-gradient(-90deg, white 0%, #975FE4 100%)',
              fillOpacity: 0.6,
              width: '100%',
            }}
            padding={-20}
            data={visitData}
          />
        </ChartCard>
      </Col>

      <Col {...topColResponsiveProps}>
        <ChartCard
          bordered={false}
          loading={loading}
          title="访问量"
          total={numeral(introduceRow?.pv).format('0,0')}
          footer={
            <Field label="在线用户" value={numeral(introduceRow?.onLineUserCount).format('0,0')} />
          }
          contentHeight={46}
        >
          <Area
            xField="x"
            yField="y"
            shapeField="smooth"
            height={46}
            axis={false}
            style={{
              fill: 'linear-gradient(-90deg, white 0%, #975FE4 100%)',
              fillOpacity: 0.6,
              width: '100%',
            }}
            padding={-20}
            data={visitData}
          />
        </ChartCard>
      </Col>
    </Row>
  );
};
export default IntroduceRow;
