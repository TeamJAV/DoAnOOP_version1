import React, { Component } from "react";
import { Form, Tabs, Tab } from "react-bootstrap";
import ProfitSum from "../components/statistic/ProfitSum";

export default class TransactionScreen extends Component {
  state = {
    time: "today",
    type: "selling",
    invoice: "",
  };
  timeOptions = {
    today: "Hôm nay",
    this_week: "Tuần này",
    this_month: "Tháng này",
  };
  componentDidUpdate(prevProps, prevState) {
    if (
      prevState.time !== this.state.time ||
      prevState.type !== this.state.type
    ) {
      this.getInvoice();
    }
  }
  handleChangeTime = (event) => {
    const time = event.target.value;
    this.setState({
      time: time,
    });
  };
  componentDidMount() {
    console.log("abc");
  }
  getInvoice = () => {
    fetch(
      `http://localhost:8081/statistic/trans?time=${this.state.time}&type=${this.state.type}`,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    )
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        this.setState({ invoice: data });
      })
      .then(() => {
        console.log(this.state.invoice);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  render() {
    return (
      <>
        <div>
          <div>
            <Form>
              {["today", "this_week", "this_month"].map((val) => (
                <Form.Check
                  inline
                  label={this.timeOptions[val]}
                  name="time"
                  type="radio"
                  value={val}
                  key={val}
                  defaultChecked={val === "today" ? true : false}
                  onChange={this.handleChangeTime}
                />
              ))}
            </Form>
          </div>
          <div>
            <Tabs
              id="controlled-tab"
              activeKey={this.state.type}
              onSelect={(type) => {
                this.setState({ type: type });
              }}
              transition={false}
            >
              <Tab eventKey="selling" title="Bán">
                <div>abc</div>
              </Tab>
              <Tab eventKey="import" title="Nhập">
                <div>abc</div>
              </Tab>
              <Tab eventKey="refund" title="Trả">
                <div>abc</div>
              </Tab>
            </Tabs>
          </div>
          <div>
            <ProfitSum time={this.state.time}></ProfitSum>
          </div>
        </div>
      </>
    );
  }
}
