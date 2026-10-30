(define (problem problem-test4)
  (:domain Sokoban)
  (:objects
    box0 - box
    box1 - box
    box2 - box
    U D L R - direction
    pos_0_0 - position
    pos_6_0 - position
    pos_0_1 - position
    pos_2_1 - position
    pos_3_1 - position
    pos_4_1 - position
    pos_6_1 - position
    pos_2_2 - position
    pos_3_2 - position
    pos_4_2 - position
    pos_1_3 - position
    pos_2_3 - position
    pos_3_3 - position
    pos_4_3 - position
    pos_5_3 - position
    pos_1_4 - position
    pos_2_4 - position
    pos_3_4 - position
    pos_4_4 - position
    pos_5_4 - position
    pos_4_5 - position
    pos_5_5 - position
    pos_0_6 - position
    pos_1_6 - position
    pos_2_6 - position
  )
  (:init
    (guard-at pos_4_5)
    (box-at pos_4_2)
    (box-at pos_3_4)
    (box-at pos_4_4)
    (clear pos_0_0)
    (adjacent pos_0_0 pos_0_1 D)
    (clear pos_6_0)
    (adjacent pos_6_0 pos_6_1 D)
    (clear pos_0_1)
    (adjacent pos_0_1 pos_0_0 U)
    (clear pos_2_1)
    (adjacent pos_2_1 pos_2_2 D)
    (adjacent pos_2_1 pos_3_1 R)
    (clear pos_3_1)
    (adjacent pos_3_1 pos_3_2 D)
    (adjacent pos_3_1 pos_2_1 L)
    (adjacent pos_3_1 pos_4_1 R)
    (clear pos_4_1)
    (adjacent pos_4_1 pos_4_2 D)
    (adjacent pos_4_1 pos_3_1 L)
    (clear pos_6_1)
    (adjacent pos_6_1 pos_6_0 U)
    (clear pos_2_2)
    (adjacent pos_2_2 pos_2_1 U)
    (adjacent pos_2_2 pos_2_3 D)
    (adjacent pos_2_2 pos_3_2 R)
    (clear pos_3_2)
    (adjacent pos_3_2 pos_3_1 U)
    (adjacent pos_3_2 pos_3_3 D)
    (adjacent pos_3_2 pos_2_2 L)
    (adjacent pos_3_2 pos_4_2 R)
    (adjacent pos_4_2 pos_4_1 U)
    (adjacent pos_4_2 pos_4_3 D)
    (adjacent pos_4_2 pos_3_2 L)
    (clear pos_1_3)
    (adjacent pos_1_3 pos_1_4 D)
    (adjacent pos_1_3 pos_2_3 R)
    (clear pos_2_3)
    (adjacent pos_2_3 pos_2_2 U)
    (adjacent pos_2_3 pos_2_4 D)
    (adjacent pos_2_3 pos_1_3 L)
    (adjacent pos_2_3 pos_3_3 R)
    (clear pos_3_3)
    (adjacent pos_3_3 pos_3_2 U)
    (adjacent pos_3_3 pos_3_4 D)
    (adjacent pos_3_3 pos_2_3 L)
    (adjacent pos_3_3 pos_4_3 R)
    (clear pos_4_3)
    (adjacent pos_4_3 pos_4_2 U)
    (adjacent pos_4_3 pos_4_4 D)
    (adjacent pos_4_3 pos_3_3 L)
    (adjacent pos_4_3 pos_5_3 R)
    (clear pos_5_3)
    (adjacent pos_5_3 pos_5_4 D)
    (adjacent pos_5_3 pos_4_3 L)
    (clear pos_1_4)
    (adjacent pos_1_4 pos_1_3 U)
    (adjacent pos_1_4 pos_2_4 R)
    (clear pos_2_4)
    (adjacent pos_2_4 pos_2_3 U)
    (adjacent pos_2_4 pos_1_4 L)
    (adjacent pos_2_4 pos_3_4 R)
    (adjacent pos_3_4 pos_3_3 U)
    (adjacent pos_3_4 pos_2_4 L)
    (adjacent pos_3_4 pos_4_4 R)
    (adjacent pos_4_4 pos_4_3 U)
    (adjacent pos_4_4 pos_4_5 D)
    (adjacent pos_4_4 pos_3_4 L)
    (adjacent pos_4_4 pos_5_4 R)
    (clear pos_5_4)
    (adjacent pos_5_4 pos_5_3 U)
    (adjacent pos_5_4 pos_5_5 D)
    (adjacent pos_5_4 pos_4_4 L)
    (adjacent pos_4_5 pos_4_4 U)
    (adjacent pos_4_5 pos_5_5 R)
    (clear pos_5_5)
    (adjacent pos_5_5 pos_5_4 U)
    (adjacent pos_5_5 pos_4_5 L)
    (clear pos_0_6)
    (adjacent pos_0_6 pos_1_6 R)
    (clear pos_1_6)
    (adjacent pos_1_6 pos_0_6 L)
    (adjacent pos_1_6 pos_2_6 R)
    (clear pos_2_6)
    (adjacent pos_2_6 pos_1_6 L)
  )
  (:goal
    (and
      (box-at pos_2_2)
      (box-at pos_3_2)
      (box-at pos_3_3)
    )
  )
)
