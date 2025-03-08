import Slider from "./Slider.tsx";
import Display from "./Display.tsx";
import Contact from "./Contact.tsx";

const HomePage : React.FC = () => {
    return (
        <>
            <Slider />
            <Display title={'Phim đang chiếu'} active={'ACTIVE'} key={1}/>
            <Display title={'Phim sắp chiếu'} active={'COMMING_SOON'} key={2}/>
            <Contact/>
        </>
    )
}
export default HomePage;