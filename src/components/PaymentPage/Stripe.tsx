import {loadStripe} from "@stripe/stripe-js";
import {PUBLIC_KEY} from "../../Helper/Contanst.ts";
import { Elements } from "@stripe/react-stripe-js"
import React, {lazy} from "react";

const FormStripe = lazy(()=>import('./FormStripe.tsx'));

interface Props {
    setStep : (arg : number) => void
}

const Stripe : React.FC<Props> = ( props : Props) => {

    const stripeTestMode = loadStripe(PUBLIC_KEY);

    return (
        <Elements stripe={stripeTestMode}>
            <FormStripe setStep={props.setStep}/>
        </Elements>
    )
};
export default Stripe;