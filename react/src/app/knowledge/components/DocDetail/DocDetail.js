import React, { Component } from 'react';
import { throttle } from 'lodash';
import { Icon } from 'choerodon-ui';
import DocLog from '../DocLog';
import ResizeAble from '../ResizeAble';
import DocComment from '../DocComment';
import DocAttachment from '../DocAttachment';
import DocDetailNav from './components/DocDetailNav';

import './DocDetail.scss';

class DocDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {};
    this.container = React.createRef();
  }

  handleResizeEnd = ({ width }) => {
    localStorage.setItem('knowledge.docDetail.width', `${width}px`);
  };

  setQuery=(width = this.container.current.clientWidth) => {
    if (width <= 600) {
      this.container.current.setAttribute('max-width', '600px');
    } else {
      this.container.current.removeAttribute('max-width');
    }
  };

  handleResize = throttle(({ width }) => {
    this.setQuery(width);
  }, 150);

  render() {
    const { onCollapse, currentNav, mode } = this.props;

    return (
      <div className="c7n-docDetail">
        <ResizeAble
          modes={['left']}
          size={{
            maxWidth: 800,
            minWidth: 440,
          }}
          defaultSize={{
            width: localStorage.getItem('knowledge.docDetail.width') || 440,
            height: '100%',
          }}
          onResizeEnd={this.handleResizeEnd}
          onResize={this.handleResize}
        >
          <div className="c7n-docDetail-wrapper" ref={this.container}>
            <DocDetailNav currentNav={currentNav} mode={mode} />
            <div className="c7n-docDetail-content">
              <div className="c7n-docDetail-header">
                <div className="c7n-docDetail-title">
                  {'文档信息'}
                </div>
                <div
                  className="c7n-docDetail-collapse"
                  role="none"
                  onClick={onCollapse}
                >
                  <Icon type="last_page" style={{ fontSize: '18px', fontWeight: '500' }} />
                  <span>隐藏详情</span>
                </div>
              </div>
              <div className="c7n-docDetail-body" id="scroll-area">
                <DocAttachment {...this.props} mode={mode} />
                {mode !== 'share'
                  ? (
                    <DocComment {...this.props} />
                  ) : null
                }
                {mode !== 'share'
                  ? (
                    <DocLog {...this.props} />
                  ) : null
                }
              </div>
            </div>
          </div>
        </ResizeAble>
      </div>
    );
  }
}

export default DocDetail;
