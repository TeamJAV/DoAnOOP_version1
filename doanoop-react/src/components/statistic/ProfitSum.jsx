import React, { Component } from "react";

export default class ProfitSum extends Component {
  state = {
    summary: "",
    isFetching: true,
  };

  componentDidMount() {
    this.setSummary();
  }
  componentDidUpdate(prevProps, prevState) {
    if (
      prevProps.time === this.props.time &&
      prevProps.specTime === this.props.specTime
    ) {
      return;
    }
    this.setSummary();
  }
  setSummary = () => {
    const specTime = this.props.specTime;
    const url =
      this.props.time === "specific"
        ? `http://localhost:8081/statistic/money?from=${specTime.from}&to=${specTime.to}`
        : `http://localhost:8081/statistic/money?time=${this.props.time}`;

    fetch(url, {
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        console.log(data);
        this.setState({
          summary: data,
          isFetching: false,
        });
      })
      .catch((err) => {
        console.log(err);
      });
  };
  render() {
    return (
      <>
        <div className="profit-summary general-container">
          {this.state.isFetching ? (
            <div>Đang tính toán...</div>
          ) : (
            <>
              <div className="profit-summary__row">
                <div className="profit-summary__column">Tổng thu:</div>
                <div className="profit-summary__column text-right">
                  {this.state.summary[0].TotalCollect}
                </div>
              </div>
              <div className="profit-summary__row">
                <div className="profit-summary__column">Tổng chi:</div>
                <div className="profit-summary__column text-right">
                  {this.state.summary[0].TotalPay}
                </div>
              </div>
              <div className="profit-summary__row">
                <div className="profit-summary__column">Tổng lãi:</div>
                <div className="profit-summary__column text-right">
                  {this.state.summary[0].TotalInterest}
                </div>
              </div>
            </>
          )}
        </div>
      </>
    );
  }
}
