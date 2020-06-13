import React, { Component } from "react";
import { Tabs, Tab } from "react-bootstrap";
import BatchInputContainer from "./BatchInputContainer";
import ProductInputForm from "./ProductInputForm";

export default class ImportTab extends Component {
  render() {
    return (
      <>
        <div className="import-container">
          <Tabs
            className="justify-content-center"
            defaultActiveKey="batchInputForm"
            id="uncontrolled-tab-example"
            transition={false}
          >
            <Tab eventKey="batchInputForm" title="Phiếu nhập hàng">
              <BatchInputContainer />
            </Tab>
            <Tab eventKey="profile" title="Thêm mới hàng">
              <ProductInputForm />
            </Tab>
          </Tabs>
        </div>
      </>
    );
  }
}
