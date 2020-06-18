import React, { Component } from "react";
import { Form, Tabs, Tab } from "react-bootstrap";
import ProfitSum from "../components/statistic/ProfitSum";
import NavigationBar from "../components/NavigationBar";
import InvoiceList from "../components/InvoiceList";
import "../style/css/transactionScreen.css"

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
  typeOptions = {
    selling: "Bán",
    import: "Nhập",
    refund: "Trả",
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
  // componentDidMount() {
  //   console.log("abc");
  // }
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
      .catch((err) => {
        console.log(err);
      });
  };
  render() {
    return (
      <>
        <NavigationBar pathname="/trans-stats"></NavigationBar>
        <div className="container statistic-container">
          <div className="statistic-container__invoice">
            <Form className="statistic-form">
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
            <Tabs
              id="controlled-tab"
              activeKey={this.state.type}
              onSelect={(type) => {
                this.setState({ type: type });
              }}
              transition={false}
              unmountOnExit={true}
            >
              {["selling", "import", "refund"].map((val) => (
                <Tab key={val} eventKey={val} title={this.typeOptions[val]}>
                  <InvoiceList
                    type={this.state.type}
                    time={this.state.time}
                  ></InvoiceList>
                </Tab>
              ))}
            </Tabs>
          </div>
          <div className="statistic-container__modules">
            <ProfitSum time={this.state.time}></ProfitSum>
          </div>
        </div>
      </>
    );
  }
}
