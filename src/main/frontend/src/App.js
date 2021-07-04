import logo from "./logo.svg";
import "./App.css";
import { useEffect, useState } from "react";
import axios from "axios";

const countries = {
  KRW: "한국(KRW)",
  USD: "미국(USD)",
  PHP: "필리핀(PHP)",
  JPY: "일본(JPY)",
};

const App = () => {
  const [sendCountry, setSendCountry] = useState("USD");
  const [receiveCountry, setReceiveCountry] = useState("KRW");
  const [price, setPrice] = useState();
  const [currency, setCurrency] = useState(0);
  const [totalPrice, setTotalPrice] = useState();
  useEffect(() => {
    const params = {
      currency: receiveCountry,
    };
    axios
      .get("http://localhost:8080/api/exchange", { params })
      .then((res) => setCurrency(res.data));
  }, [receiveCountry]);

  const formatPrice = (num) => {
    return num.toFixed(2).replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
  };

  const handleReceiveCountry = (event) => {
    const country = event.target.value;
    setReceiveCountry(country);
    setTotalPrice();
  };

  const sendPriceChange = (event) => {
    const number = event.target.value;
    if (!isNaN(number)) {
      setPrice(number);
    } else {
      alert("숫자만 입력할 수 있습니다.");
    }
  };

  const calculatePrice = () => {
    const num = parseInt(price);
    if (num < 0 || num > 10000 || isNaN(num))
      return alert("송금액이 올바르지 않습니다");
    const calculate = formatPrice(num * currency);
    setTotalPrice(calculate);
  };

  return (
    <div className="App">
      <h1>환율 계산</h1>
      <div>
        <p>송금국가: {countries[sendCountry]}</p>
        <p>
          수취국가:
          <select defaultValue={receiveCountry} onChange={handleReceiveCountry}>
            {Object.entries(countries).map(([key, value]) => {
              if (key !== sendCountry) {
                return <option value={key}>{value}</option>;
              }
            })}
          </select>
        </p>
        <p>{`환율: ${formatPrice(
          currency
        )} ${receiveCountry}/${sendCountry}`}</p>
        <p>
          송금액:
          <input
            className="sendPriceInput"
            onChange={sendPriceChange}
            value={price}
          />
          {sendCountry}
        </p>
        <button onClick={calculatePrice}>Submit</button>
      </div>
      {totalPrice && (
        <h3>{`수취금액은 ${totalPrice} ${receiveCountry} 입니다.`}</h3>
      )}
    </div>
  );
};

export default App;
