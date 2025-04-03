import { useState } from "react";
import { CardElement, useElements, useStripe } from "@stripe/react-stripe-js";
import axios from "axios";
import {env} from "../../Helper/Contanst.ts";
import {useCommonContext} from "../../context/CommonContext.tsx";
import {Spin} from "antd";
import {toast} from "react-toastify";

const CARD_OPTION = {
    iconStyle: "solid",
    style: {
        base: {
            iconColor: "#f7ac00",
            color: "#d6e3ee",
            fontWeight: 500,
            fontFamily: "Roboto, Open Sans, Segoe UI, sans-serif",
            fontSize: "16px",
            fontSmoothing: "antialiased",
            ":-webkit-autofill": { color: "#d6e3ee" },
            "::placeholder": { color: "#d6e3ee" },
        },
        invalid: {
            iconColor: "#d6e3eess",
            color: "#d6e3ee",
        },
    },
};

interface Props {
    setStep : (arg : number) => void
}
const FormStripe : React.FC<Props> = ( props : Props ) => {

    const [errorMessage, setErrorMessage] = useState("");
    const stripe = useStripe();
    const elements = useElements();
    const {bill,setBill, initBill} = useCommonContext();
    const [loading, setLoading] = useState<boolean>(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage(""); // Reset error message

        // Tạo payment method
        const { error, paymentMethod } = await stripe.createPaymentMethod({
            type: "card",
            card: elements.getElement(CardElement),
        });

        if (error) {
            setErrorMessage(error.message);
            return;
        }

        try {
            const { id } = paymentMethod;
            setLoading(true);
            const response = await axios.post(`${env.url.API_BASE_URL}/payment-service/api/bill/payment`, {
                id: id,
                amount: bill?.totalPrice,
                currency : "VND",
                billId : bill?.id
            });
            setLoading(false);
            if (response.data.data.success) {
                props.setStep(3);
                setBill(response.data.data.bill);
                toast.success(<p className={'w-full'}>Đặt vé thành công</p>)
            } else {
                setErrorMessage("Payment failed. Please try again.");
            }
        } catch (error) {
            setErrorMessage("Error processing payment: " + error.message);
            console.log( error);
            setLoading(false);
        }
    };

    return (
        <>
            <Spin spinning={loading} tip={"Đang tải..."} size={"default"}>
                <form onSubmit={handleSubmit}>
                    <fieldset className="FormGroup">
                        <div className="FormRow">
                            <CardElement options={CARD_OPTION}/>
                        </div>
                    </fieldset>
                    {errorMessage && <p style={{color: "red"}}>{errorMessage}</p>}
                    <button className={'px-4 py-2 bg-main text-textCol w-[150px] mt-2'}>Pay</button>
                </form>
            </Spin>
        </>
    )
};
export default FormStripe;