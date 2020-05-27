import React, { Component } from "react";
import { Tabs, Tab } from "react-bootstrap";
import BatchInputForm from "./BatchInputForm";

export default class ImportTab extends Component {
  render() {
    return (
      <>
        <Tabs defaultActiveKey="batchInputForm" id="uncontrolled-tab-example">
          <Tab eventKey="batchInputForm" title="Phiếu nhập hàng">
            <BatchInputForm />
          </Tab>
          <Tab eventKey="profile" title="Thêm mới hàng"></Tab>
        </Tabs>
      </>
    );
  }
}
