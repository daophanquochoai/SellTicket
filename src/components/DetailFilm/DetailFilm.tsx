import Description from "./Description.tsx";
import RatePage from "./RatePage.tsx";
import BookTicket from "./BookTicket.tsx";
import MovieTheater from "./MovieTheater.tsx";
import ChooseChair from "./ChooseChair.tsx";

const DetailFilm : React.FC = () => {
    return (
        <>
            <Description />
            <RatePage />
            <BookTicket />
            <MovieTheater />
            <ChooseChair />
        </>
    )
}
export default DetailFilm;