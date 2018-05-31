package com.chess.engine.pieces;

import java.util.*;
import com.chess.engine.*;
import com.chess.engine.board.*;
import static com.chess.engine.board.Move.*;
import com.google.common.collect.ImmutableList;

/**
 * Created by Tom on 19/01/2017.
 * ///
 */

public class Bishop extends Piece {

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9,-7,7,9};

    public Bishop(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.BISHOP,piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidatesCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES ){

            int candidateDestinationCoordinate = this.piecePosition;

            while (BoardUtils.isValidTitleCoordinate(candidateDestinationCoordinate)){

                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidatesCoordinateOffset) ||
                        isEightColumnExclusion(candidateDestinationCoordinate, candidatesCoordinateOffset)){
                    break;
                }

                candidateDestinationCoordinate += candidatesCoordinateOffset;
                if (BoardUtils.isValidTitleCoordinate(candidateDestinationCoordinate)){
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()){
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }else {
                        final  Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if(this.pieceAlliance != pieceAlliance){
                            legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }


                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.BISHOP.toString();
    }


    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);

    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -7|| candidateOffset == 9);

    }
}
