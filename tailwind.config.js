import colors from "tailwindcss/colors.js";

/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}"
  ],
  theme: {
    extend: {
      colors : {
        main : "#f7ac00",
        foreground : "#111827",
        bodyBg : "#181a1b",
        border : "#172338",
        textCol : "#d6e3ee",
        textAdmin : "#7D7A7A"
      },
      fontSize : {
        medium : "18px"
      },
      keyframes : {
        icon : {
          '0%' : { scale : "100%"},
          '50%' : {scale: "120%"},
          '100%' : {scale: "100%"}
        },
        button : {
          '0%' : {width : '0px'},
          '100%' : {width : '100%'}
        }
      },
      animation : {
        iconAni : 'icon 1.5s linear infinite',
        buttonScale : 'button 1.5s all'
      }
    },
  },
  plugins: []
}
