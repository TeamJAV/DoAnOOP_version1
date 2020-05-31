import React, { Component } from "react";
import { Tabs, Tab } from "react-bootstrap";
import BatchInputContainer from "./BatchInputContainer";

export default class ImportTab extends Component {
  render() {
    return (
      <>
        <Tabs defaultActiveKey="batchInputForm" id="uncontrolled-tab-example">
          <Tab eventKey="batchInputForm" title="Phiếu nhập hàng">
            <BatchInputContainer />
          </Tab>
          <Tab eventKey="profile" title="Thêm mới hàng"></Tab>
        </Tabs>
      </>
    );
  }
}
